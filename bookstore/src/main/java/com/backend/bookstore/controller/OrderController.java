package com.backend.bookstore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.bookstore.entity.Order;
import com.backend.bookstore.service.OrderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RestController
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/orders/all")
    public ResponseEntity<List<Order>> getAllOrders() {

        try {

            List<Order> getAll = orderService.getAllOrders();
            return ResponseEntity.status(HttpStatus.OK).body(getAll);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(Long id) {

        try {
            Order orderid = orderService.getOrderById(id);
            return ResponseEntity.status(HttpStatus.OK).body(orderid);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/order")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order) {

        try {
            Order orderCreated = orderService.createOrder(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderCreated);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }
}
