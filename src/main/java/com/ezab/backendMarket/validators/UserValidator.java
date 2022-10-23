package com.ezab.backendMarket.validators;

import com.ezab.backendMarket.entities.User;
import com.ezab.backendMarket.exceptions.ValidateServiceException;

public class UserValidator {

    public static void signup(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty())
            throw new ValidateServiceException("The username is required");
        if (user.getUsername().length() > 30)
            throw new ValidateServiceException("The username is too long");
        if (user.getPassword() == null || user.getPassword().isEmpty())
            throw new ValidateServiceException("The password is required");
        if (user.getPassword().length() > 15)
            throw new ValidateServiceException("The password is too long");

    }

}
