package com.lpnu.excursionclient.controller;

import com.lpnu.excursionclient.dto.request.order.CancelOrderRequest;
import com.lpnu.excursionclient.dto.request.order.CreateIndividualExcursionOrderRequest;
import com.lpnu.excursionclient.dto.request.order.CreatePlannedExcursionOrderRequest;
import com.lpnu.excursionclient.dto.response.order.CancelOrderResponse;
import com.lpnu.excursionclient.dto.response.order.CreateIndividualExcursionOrderResponse;
import com.lpnu.excursionclient.dto.response.order.CreatePlannedExcursionOrderResponse;
import com.lpnu.excursionclient.dto.response.order.GetAllOrderListResponse;
import com.lpnu.excursionclient.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/planned")
    public ResponseEntity<CreatePlannedExcursionOrderResponse> createPlannedExcursionOrder(
            @Valid @RequestBody CreatePlannedExcursionOrderRequest createPlannedExcursionOrderRequest,
            Authentication authentication
    ) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/order/planned").toUriString());
        orderService.createPlannedExcursionOrder(createPlannedExcursionOrderRequest, (String) authentication.getPrincipal());
        return ResponseEntity.created(uri).body(new CreatePlannedExcursionOrderResponse("success"));
    }

    @PostMapping("/individual")
    public ResponseEntity<CreateIndividualExcursionOrderResponse> createIndividualExcursionOrder(
            @Valid @RequestBody CreateIndividualExcursionOrderRequest createIndividualExcursionOrderRequest,
            Authentication authentication
    ) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/order/individual").toUriString());
        orderService.createIndividualExcursionOrder(createIndividualExcursionOrderRequest, (String) authentication.getPrincipal());
        return ResponseEntity.created(uri).body(new CreateIndividualExcursionOrderResponse("success"));
    }

    @GetMapping("/all")
    public ResponseEntity<GetAllOrderListResponse> getClientOrders(
            @RequestParam(name = "page_number", defaultValue = "0") Integer pageNumber,
            @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize,
            Authentication authentication
    ) {
        return ResponseEntity.ok().body(new GetAllOrderListResponse(
                orderService.getClientOrders((String) authentication.getPrincipal(), pageNumber, pageSize)));
    }

    @PutMapping("/cancel")
    public ResponseEntity<CancelOrderResponse> cancelOrder(
            @Valid @RequestBody CancelOrderRequest cancelOrderRequest,
            Authentication authentication
    ) {
        return ResponseEntity.ok().body(orderService.cancelOrder(
                cancelOrderRequest, (String) authentication.getPrincipal()));
    }
}
