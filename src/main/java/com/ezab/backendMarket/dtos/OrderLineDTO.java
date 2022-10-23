package com.ezab.backendMarket.dtos;


import com.ezab.backendMarket.entities.Order;
import com.ezab.backendMarket.entities.Product;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineDTO {

    private Long id;
    private ProductDTO product;
    private Double price;
    private Double quantity;
    private Double total;


}
