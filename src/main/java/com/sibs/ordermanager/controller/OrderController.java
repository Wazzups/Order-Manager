package com.sibs.ordermanager.controller;

import com.sibs.ordermanager.model.Order;
import com.sibs.ordermanager.service.interfaces.OrderService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @Autowired
  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
    Order savedOrder = orderService.saveOrder(order);
    return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
    return orderService.getOrderById(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Order>> getAllOrders() {
    List<Order> orders = orderService.getAllOrders();
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
    return orderService.getOrderById(id).map(order -> {
      order.setItem(orderDetails.getItem());
      order.setQuantity(orderDetails.getQuantity());
      order.setUser(orderDetails.getUser());

      Order updatedOrder = orderService.saveOrder(order);
      return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
    return orderService.getOrderById(id).map(order -> {
      orderService.deleteOrder(id);
      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }
}