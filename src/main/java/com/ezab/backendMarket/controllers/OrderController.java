package com.ezab.backendMarket.controllers;

import com.ezab.backendMarket.converters.OrderConverter;
import com.ezab.backendMarket.dtos.OrderDTO;
import com.ezab.backendMarket.entities.Order;
import com.ezab.backendMarket.services.OrderService;
import com.ezab.backendMarket.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderConverter converter;

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/order")
    public ResponseEntity<WrapperResponse<List<OrderDTO>>> findAllOrders(
            @RequestParam(
                    name = "pageNumber",
                    required = false,
                    defaultValue = "0") int pageNumber,
            @RequestParam(
                    name = "pageSize",
                    required = false,
                    defaultValue = "5") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<Order> orders = orderService.findAllOrders(pageable);
        List<OrderDTO> ordersDTO = converter.fromEntity(orders);
        return new WrapperResponse<>(
                true,
                "Success",
                ordersDTO
        ).createResponse();
    }

    @GetMapping(value = "/order/{id}")
    public ResponseEntity<WrapperResponse<OrderDTO>> findOrderById(
            @PathVariable(value = "id") Long id
    ) {
        Order order = orderService.findOrderById(id);
        OrderDTO orderDTO = converter.fromEntity(order);

        WrapperResponse<OrderDTO> response = new WrapperResponse<>(
                true,
                "Success",
                orderDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping(value = "/order")
    public ResponseEntity<WrapperResponse<OrderDTO>> createOrder(
            @RequestBody OrderDTO order
    ) {
        Order newOrder = orderService.saveOrder(converter.fromDTO(order));
        OrderDTO orderDTO = converter.fromEntity(newOrder);

        return new WrapperResponse<OrderDTO>(
                true,
                "Success",
                orderDTO
        ).createResponse();
    }


    @PutMapping(value = "/order")
    public ResponseEntity<WrapperResponse<OrderDTO>> updateOrder(
            @RequestBody OrderDTO order
    ) {
        Order newOrder = orderService.saveOrder(converter.fromDTO(order));
        OrderDTO orderDTO = converter.fromEntity(newOrder);

        return new WrapperResponse<OrderDTO>(
                true,
                "Success",
                orderDTO
        ).createResponse();
    }

    @DeleteMapping(value = "/order/{id}")
    public ResponseEntity<?> deleteOrder(
            @PathVariable(value = "id") Long id
    ) {
        orderService.deleteOrderById(id);
        return new WrapperResponse<>(
                true,
                "Success",
                null
        ).createResponse();
    }

}
