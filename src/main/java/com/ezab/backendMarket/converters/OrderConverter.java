package com.ezab.backendMarket.converters;

import com.ezab.backendMarket.dtos.OrderDTO;
import com.ezab.backendMarket.dtos.OrderLineDTO;
import com.ezab.backendMarket.dtos.ProductDTO;
import com.ezab.backendMarket.entities.Order;
import com.ezab.backendMarket.entities.OrderLine;
import lombok.AllArgsConstructor;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OrderConverter extends AbstractConverter<Order, OrderDTO> {

    private DateTimeFormatter dateTimeFormatter;

    private final ProductConverter productConverter;

    private UserConverter userConverter;

    @Override
    public OrderDTO fromEntity(Order entity) {
        if (entity == null) return null;
        List<OrderLineDTO> lines = fromOrderLineEntity(entity.getLines());
        return OrderDTO.builder()
                .id(entity.getId())
                .lines(lines)
                .regDate(entity.getRegDate().format(dateTimeFormatter))
                .total(entity.getTotal())
                .userDTO(userConverter.fromEntity(entity.getUser()))
                .build();
    }

    @Override
    public Order fromDTO(OrderDTO dto) {
        if (dto == null) return null;
        List<OrderLine> lines = fromOrderLineDTO(dto.getLines());
        return Order.builder()
                .id(dto.getId())
                .lines(lines)
                .total(dto.getTotal())
                .user(userConverter.fromDTO(dto.getUserDTO()))
                .build();
    }

    private List<OrderLineDTO> fromOrderLineEntity(List<OrderLine> lines) {
        if (lines == null) return null;
        return lines.stream().map(line -> {
            return OrderLineDTO.builder()
                    .id(line.getId())
                    .price(line.getPrice())
                    .product(productConverter.fromEntity(line.getProduct()))
                    .quantity(line.getQuantity())
                    .total(line.getTotal())
                    .build();
        }).collect(Collectors.toList());
    }

    private List<OrderLine> fromOrderLineDTO(List<OrderLineDTO> lines) {
        if (lines == null) return null;
        return lines.stream().map(line -> {
            return OrderLine.builder()
                    .id(line.getId())
                    .price(line.getPrice())
                    .product(productConverter.fromDTO(line.getProduct()))
                    .quantity(line.getQuantity())
                    .total(line.getTotal())
                    .build();
        }).collect(Collectors.toList());
    }
}
