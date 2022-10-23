package com.ezab.backendMarket.dtos;

import com.ezab.backendMarket.entities.OrderLine;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private String regDate;
    private List<OrderLineDTO> lines;
    private Double total;
    private UserDTO userDTO;
}
