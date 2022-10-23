package com.ezab.backendMarket.controllers;

import com.ezab.backendMarket.converters.ProductConverter;
import com.ezab.backendMarket.dtos.ProductDTO;
import com.ezab.backendMarket.entities.Product;
import com.ezab.backendMarket.repositories.ProductRepository;
import com.ezab.backendMarket.services.ProductService;
import com.ezab.backendMarket.utils.WrapperResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Wrapper;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductConverter converter;

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<WrapperResponse<List<ProductDTO>>> findAllProducts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "0") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize
    ) {
        Pageable page = PageRequest.of(pageNumber, pageSize);

        List<Product> products = productService.findAllProducts(page);

        List<ProductDTO> productsDTO = converter.fromEntity(products);

        WrapperResponse<List<ProductDTO>> response = new WrapperResponse<>(
                true,
                "Success",
                productsDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public ResponseEntity<WrapperResponse<ProductDTO>> findProductById(@PathVariable(value = "id") Long id) {
        Product product = productService.findProductById(id);

        ProductDTO productDTO = converter.fromEntity(product);

        WrapperResponse<ProductDTO> response = new WrapperResponse<>(
                true,
                "Success",
                productDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping(value = "/product")
    public ResponseEntity<WrapperResponse<ProductDTO>> createProduct(@RequestBody ProductDTO newProduct) {
        Product productAdded = productService.saveProduct(converter.fromDTO(newProduct));

        ProductDTO productDTO = converter.fromEntity(productAdded);

        WrapperResponse<ProductDTO> response = new WrapperResponse<>(
                true,
                "Success",
                productDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/product")
    public ResponseEntity<WrapperResponse<ProductDTO>> updateProductById(@RequestBody ProductDTO product) {
        Product productUpdated = productService.saveProduct(converter.fromDTO(product));

        ProductDTO productDTO = converter.fromEntity(productUpdated);

        WrapperResponse<ProductDTO> response = new WrapperResponse<>(
                true,
                "Success",
                productDTO
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable(value = "id") Long id) {
        productService.deleteProductById(id);
        return new WrapperResponse<>(
                true,
                "Success",
                null
        ).createResponse();
    }



}
