package com.lpnu.excursionclient.service;

import com.lpnu.excursionclient.dto.request.order.CancelOrderRequest;
import com.lpnu.excursionclient.dto.request.order.CreateIndividualExcursionOrderRequest;
import com.lpnu.excursionclient.dto.request.order.CreatePlannedExcursionOrderRequest;
import com.lpnu.excursionclient.dto.response.order.CancelOrderResponse;
import com.lpnu.excursionclient.dto.response.order.GetAllOrderItemResponse;
import org.springframework.data.domain.Page;

public interface OrderService {
    void createPlannedExcursionOrder(CreatePlannedExcursionOrderRequest createPlannedExcursionOrderRequest, String email);

    void createIndividualExcursionOrder(CreateIndividualExcursionOrderRequest createIndividualExcursionOrderRequest, String email);

    Page<GetAllOrderItemResponse> getClientOrders(String email, Integer pageNumber, Integer pageSize);

    CancelOrderResponse cancelOrder(CancelOrderRequest cancelOrderRequest, String email);
}
