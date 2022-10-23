package com.ezab.backendMarket.validators;

import com.ezab.backendMarket.entities.Order;
import com.ezab.backendMarket.entities.OrderLine;
import com.ezab.backendMarket.exceptions.ValidateServiceException;

public class OrderValidator {

    public static void validateSave(Order order) {
        if (order.getLines().isEmpty() || order.getLines() == null)
            throw new ValidateServiceException("The order doesn't have lines");
        for (OrderLine line: order.getLines()) {
            if (line.getProduct() == null || line.getProduct().getId() == null)
                throw new ValidateServiceException("The product is required");
            if (line.getQuantity() == null)
                throw new ValidateServiceException("The quantity is required");
            if (line.getQuantity() < 0)
                throw new ValidateServiceException("The quantity must not be less than zero");
        }
    }

}
