package com.ezab.backendMarket.controllers;

import com.ezab.backendMarket.entities.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class HelloWorldController {

    private static final Logger log = Logger.getLogger(HelloWorldController.class.getCanonicalName());

    @RequestMapping(value = "/helloWorld", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello world";
    }

/*
    @GetMapping(value = "/product")
    public Product findProduct() {
        log.info("Method findProduct called");
        Product myProduct = Product.builder()
                .id("123")
                .name("Leche deslactosada")
                .build();
        return myProduct;
    }
*/
}
