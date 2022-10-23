package com.ezab.backendMarket.services;


import com.ezab.backendMarket.entities.Order;
import com.ezab.backendMarket.entities.OrderLine;
import com.ezab.backendMarket.entities.Product;
import com.ezab.backendMarket.entities.User;
import com.ezab.backendMarket.exceptions.NoDataFoundException;
import com.ezab.backendMarket.exceptions.ValidateServiceException;
import com.ezab.backendMarket.repositories.OrderLineRepository;
import com.ezab.backendMarket.repositories.OrderRepository;
import com.ezab.backendMarket.repositories.ProductRepository;
import com.ezab.backendMarket.security.UserPrincipal;
import com.ezab.backendMarket.validators.OrderValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLineRepository orderLineRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> findAllOrders(Pageable pageable) {
        try {
            return orderRepository.findAll(pageable).toList();
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    public Order findOrderById(Long id) {
        try {
            return orderRepository.findById(id)
                    //.orElseThrow(() -> new NoDataFoundException("The order with id: " + id + " doesn't exist"));
                    .orElseThrow(() -> new NoDataFoundException());
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    @Transactional
    public Order saveOrder(Order order) {
        try {

            OrderValidator.validateSave(order);

            User user = UserPrincipal.getUserFromSS();

            Double totalOrder = 0.0;
            for (OrderLine line: order.getLines()) {
                Product product = productRepository.findById(line.getProduct().getId())
                        .orElseThrow(() -> new NoDataFoundException("The product with id: " + line.getProduct().getId() + " doesn't exist"));
                if (line.getId() != null)
                    orderLineRepository.findById(line.getId())
                            .orElseThrow(() -> new NoDataFoundException("Line with id: " + line.getId() + " to update doesn't exist"));

                line.setPrice(product.getPrice());
                line.setTotal(product.getPrice() * line.getQuantity());
                totalOrder += line.getTotal();
            }

            order.setTotal(totalOrder);
            order.getLines().forEach(line -> line.setOrder(order));

            if (order.getId() == null){
                order.setUser(user);
                order.setRegDate(LocalDateTime.now());
                return orderRepository.save(order);
            }

            Order orderToUpdate = orderRepository.findById(order.getId())
                    .orElseThrow(() -> new NoDataFoundException("The order with id: " + order.getId() + " doesn't exist"));

            order.setRegDate(orderToUpdate.getRegDate());

            List<OrderLine> deletedLines = orderToUpdate.getLines();
            deletedLines.removeAll(order.getLines());

            orderLineRepository.deleteAll(deletedLines);

            return orderRepository.save(order);

        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }


    @Transactional
    public void deleteOrderById(Long id) {
        try {
            Order orderToDelete = orderRepository.findById(id)
                    .orElseThrow(() -> new NoDataFoundException("Order with id: " + id + " not found."));
            orderRepository.delete(orderToDelete);
        } catch (ValidateServiceException | NoDataFoundException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

}
