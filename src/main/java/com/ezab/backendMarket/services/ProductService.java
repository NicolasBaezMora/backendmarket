package com.ezab.backendMarket.services;

import com.ezab.backendMarket.entities.Product;
import com.ezab.backendMarket.exceptions.GeneralServiceException;
import com.ezab.backendMarket.exceptions.NoDataFoundException;
import com.ezab.backendMarket.exceptions.ValidateServiceException;
import com.ezab.backendMarket.repositories.ProductRepository;
import com.ezab.backendMarket.validators.ProductValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public Product findProductById(Long id) {
        try {
            return productRepository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("Product with id " + id + " not found."));
        } catch (NoDataFoundException | ValidateServiceException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GeneralServiceException(e.getMessage());
        }

    }

    public List<Product> findAllProducts(Pageable page) {
        try {
            return productRepository.findAll(page).toList();
        } catch (NoDataFoundException | ValidateServiceException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GeneralServiceException(e.getMessage());
        }
    }

    @Transactional
    public Product saveProduct(Product product) {
        try {
            ProductValidator.validateCreate(product);

            if (product.getId() == null) {
                return productRepository.save(product);
            }
            Product productToUpdate = productRepository.findById(product.getId())
                    .orElseThrow(() -> new NoDataFoundException("Product with id " + product.getId() + " not found."));
            productToUpdate.setName(product.getName());
            productToUpdate.setPrice(product.getPrice());
            return productRepository.save(productToUpdate);
        } catch (NoDataFoundException | ValidateServiceException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GeneralServiceException(e.getMessage());
        }
    }
/*
    @Transactional
    public Product createNewProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Product product) {
        Product productToUpdate = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product with id " + product.getId() + " not found."));
        productToUpdate.setName(product.getName());
        productToUpdate.setPrice(product.getPrice());
        return productRepository.save(productToUpdate);
    }
*/
    @Transactional
    public void deleteProductById(Long id) {
        try {
            Product productToDelete = productRepository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("Product with id " + id + " not found."));
            productRepository.delete(productToDelete);
        } catch (NoDataFoundException | ValidateServiceException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GeneralServiceException(e.getMessage());
        }
    }

}
