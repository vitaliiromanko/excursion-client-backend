package com.lpnu.excursionclient.service.impl;

import com.lpnu.excursionclient.dto.request.order.CancelOrderRequest;
import com.lpnu.excursionclient.dto.request.order.CreateIndividualExcursionOrderRequest;
import com.lpnu.excursionclient.dto.request.order.CreatePlannedExcursionOrderRequest;
import com.lpnu.excursionclient.dto.response.order.CancelOrderResponse;
import com.lpnu.excursionclient.dto.response.order.GetAllOrderItemResponse;
import com.lpnu.excursionclient.dto.response.order.OrderExcursion;
import com.lpnu.excursionclient.enums.excursion.EExcursionConductingType;
import com.lpnu.excursionclient.enums.excursion.EExcursionItemStatus;
import com.lpnu.excursionclient.enums.excursion.EExcursionStatus;
import com.lpnu.excursionclient.enums.order.EOrderStatus;
import com.lpnu.excursionclient.exception.*;
import com.lpnu.excursionclient.model.client.Client;
import com.lpnu.excursionclient.model.excursion.Excursion;
import com.lpnu.excursionclient.model.excursion.ExcursionItem;
import com.lpnu.excursionclient.model.order.Order;
import com.lpnu.excursionclient.repository.client.ClientRepository;
import com.lpnu.excursionclient.repository.excursion.ExcursionItemRepository;
import com.lpnu.excursionclient.repository.excursion.ExcursionItemStatusRepository;
import com.lpnu.excursionclient.repository.excursion.ExcursionRepository;
import com.lpnu.excursionclient.repository.order.OrderRepository;
import com.lpnu.excursionclient.repository.order.OrderStatusRepository;
import com.lpnu.excursionclient.service.ClientService;
import com.lpnu.excursionclient.service.ExcursionService;
import com.lpnu.excursionclient.service.OrderService;
import com.lpnu.excursionclient.service.SmtpMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final ExcursionRepository excursionRepository;
    private final ExcursionItemRepository excursionItemRepository;
    private final ExcursionItemStatusRepository excursionItemStatusRepository;
    private final ClientRepository clientRepository;
    private final ExcursionService excursionService;
    private final ClientService clientService;
    private final SmtpMailSender smtpMailSender;

    @Override
    public void createPlannedExcursionOrder(CreatePlannedExcursionOrderRequest createPlannedExcursionOrderRequest,
                                            String email) {
        Client client = clientService.checkClientNotActivated(email);

        ExcursionItem excursionItem = excursionItemRepository.findById(
                        UUID.fromString(createPlannedExcursionOrderRequest.excursionItemId()))
                .orElseThrow(InvalidExcursionItemIdException::new);

        if (!excursionItem.getExcursion().getExcursionStatus().getName().equals(EExcursionStatus.ACTIVE.getName())) {
            throw new ExcursionNotActivatedException();
        }

        if (!excursionItem.getExcursionItemStatus().getName().equals(
                EExcursionItemStatus.RECRUITMENT_OF_PARTICIPANTS.getName())) {
            throw new ExcursionItemNotRecruitOfParticipantsException();
        }

        client.getOrders().add(
                Order.of(
                        client,
                        excursionItem,
                        orderStatusRepository.findByName(EOrderStatus.PROCESSING.getName())
                                .orElseThrow(InvalidOrderStatusNameException::new),
                        createPlannedExcursionOrderRequest.peopleNumber(),
                        createPlannedExcursionOrderRequest.comment(),
                        excursionItem.getExcursion().getActivePrice().multiply(
                                BigDecimal.valueOf(createPlannedExcursionOrderRequest.peopleNumber()))
                )
        );

        clientRepository.save(client);
    }

    @Override
    public void createIndividualExcursionOrder(CreateIndividualExcursionOrderRequest createIndividualExcursionOrderRequest,
                                               String email) {
        Client client = clientService.checkClientNotActivated(email);

        Excursion excursion = excursionRepository.findById(UUID.fromString(createIndividualExcursionOrderRequest.excursionId()))
                .orElseThrow(InvalidExcursionIdException::new);

        if (!excursion.getExcursionStatus().getName().equals(EExcursionStatus.ACTIVE.getName())) {
            throw new ExcursionNotActivatedException();
        }

        ExcursionItem excursionItem = excursionItemRepository.save(ExcursionItem.of(
                createIndividualExcursionOrderRequest.startDate(),
                excursion,
                excursionItemStatusRepository.findByName(EExcursionItemStatus.AWAITING_CONFIRMATION.getName())
                        .orElseThrow(InvalidExcursionItemStatusNameException::new)
        ));

        client.getOrders().add(
                Order.of(
                        client,
                        excursionItem,
                        orderStatusRepository.findByName(EOrderStatus.PROCESSING.getName())
                                .orElseThrow(InvalidOrderStatusNameException::new),
                        createIndividualExcursionOrderRequest.peopleNumber(),
                        createIndividualExcursionOrderRequest.comment(),
                        excursion.getActivePrice()
                )
        );

        clientRepository.save(client);
    }

    @Override
    public Page<GetAllOrderItemResponse> getClientOrders(String email, Integer pageNumber, Integer pageSize) {
        return orderRepository.findAllByClient(
                clientRepository.findByEmail(email)
                        .orElseThrow(InvalidEmailException::new),
                PageRequest.of(pageNumber, pageSize, Sort.by("creationDate").descending())
        ).map(this::convertToGetAllOrderItemResponse);
    }

    @Override
    public CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest, String email) {
        Client client = clientService.checkClientNotActivated(email);

        Order order = orderRepository.findById(UUID.fromString(cancelOrderRequest.orderId()))
                .orElseThrow(InvalidOrderIdException::new);

        if (!client.getId().equals(order.getClient().getId())) {
            throw new OrderNotBelongClientException();
        }

        if (order.getOrderStatus().getName().equals(EOrderStatus.DONE.getName())) {
            throw new OrderAlreadyCompletedException();
        }

        if (order.getExcursionItem().getExcursion().getExcursionConductingType().getName().equals(
                EExcursionConductingType.INDIVIDUAL.getName())) {
            order.getExcursionItem().setExcursionItemStatus(excursionItemStatusRepository.findByName(
                            EExcursionItemStatus.CANCELED_BY_CLIENT.getName())
                    .orElseThrow(InvalidExcursionItemStatusNameException::new));
        }

        order.setOrderStatus(orderStatusRepository.findByName(EOrderStatus.CANCELED_BY_CLIENT.getName())
                .orElseThrow(InvalidOrderStatusNameException::new));

        orderRepository.save(order);

        smtpMailSender.sendCancelOrderMessage(
                order.getExcursionItem().getExcursion().getOrganizer().getEmail(),
                order.getId().toString(),
                cancelOrderRequest.reason() == null || cancelOrderRequest.reason().isBlank() ?
                        null : cancelOrderRequest.reason()
        );

        return new CancelOrderResponse(
                order.getId().toString(),
                order.getOrderStatus().getName()
        );
    }

    private GetAllOrderItemResponse convertToGetAllOrderItemResponse(Order order) {
        return new GetAllOrderItemResponse(
                order.getId().toString(),
                convertToOrderExcursion(order.getExcursionItem()),
                order.getOrderStatus().getName(),
                order.getPeopleNumber(),
                order.getPrice(),
                order.getExcursionBlocked(),
                order.getOrganizerBlocked(),
                order.getCreationDate()
        );
    }

    private OrderExcursion convertToOrderExcursion(ExcursionItem excursionItem) {
        return new OrderExcursion(
                excursionItem.getExcursion().getId().toString(),
                excursionItem.getExcursion().getName(),
                excursionService.getExcursionFirstPhoto(excursionItem.getExcursion()),
                excursionItem.getStartDate()
        );
    }
}
