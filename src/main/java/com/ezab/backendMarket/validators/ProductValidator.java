package com.ezab.backendMarket.validators;

import com.ezab.backendMarket.entities.Product;
import com.ezab.backendMarket.exceptions.ValidateServiceException;

public class ProductValidator {

    public static void validateCreate(Product product) {
        if (product.getName() == null || product.getName().trim().isEmpty())
            throw new ValidateServiceException("The name is required");
        if (product.getName().length() > 100)
            throw new ValidateServiceException("The name is too long");
        if (product.getPrice() == null)
            throw new ValidateServiceException("The product price is required");
        if (product.getPrice() < 0)
            throw new ValidateServiceException("The product price can't be less than 0");
    }

}
