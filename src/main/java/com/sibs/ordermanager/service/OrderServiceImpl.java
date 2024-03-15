package com.sibs.ordermanager.service;

import com.sibs.ordermanager.model.Item;
import com.sibs.ordermanager.model.Order;
import com.sibs.ordermanager.model.OrderStockMovement;
import com.sibs.ordermanager.model.StockMovement;
import com.sibs.ordermanager.repository.OrderRepository;
import com.sibs.ordermanager.repository.OrderStockMovementRepository;
import com.sibs.ordermanager.service.interfaces.OrderService;
import com.sibs.ordermanager.service.interfaces.StockMovementService;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

  private final EmailService emailService;
  private final StockMovementService stockMovementService;
  private final OrderRepository orderRepository;
  private final OrderStockMovementRepository orderStockMovementRepository;

  @Autowired
  public OrderServiceImpl(EmailService emailService, StockMovementService stockMovementService,
      OrderRepository orderRepository, OrderStockMovementRepository orderStockMovementRepository) {
    this.emailService = emailService;
    this.stockMovementService = stockMovementService;
    this.orderRepository = orderRepository;
    this.orderStockMovementRepository = orderStockMovementRepository;
  }

  @Override
  public Order saveOrder(Order order) {
    List<StockMovement> stockMovements = stockMovementService.getStockMovementByItem(
        order.getItem(), Sort.by(Sort.Direction.DESC, "quantity"));

    if (tryFulfillOrder(order, stockMovements)) {
      emailService.notifyCustomerOrderFulfilled(order.getUser().getEmail(), "Order fulfilled",
          "Your order was dispatched");
    }

    log.info("Order created: {}", order);

    return orderRepository.save(order);
  }

  @Override
  public boolean tryFulfillOrder(Order order, List<StockMovement> stockMovements) {
    long orderQuantity = order.getQuantity();
    long itemStockQuantity = stockMovements.stream().mapToLong(StockMovement::getQuantity).sum();

    if (itemStockQuantity >= orderQuantity) {
      for (StockMovement stockMovement : stockMovements) {
        long stockMovementQuantity = stockMovement.getQuantity();

        if (orderQuantity <= stockMovementQuantity) {
          stockMovement.setQuantity(stockMovementQuantity - orderQuantity);
          stockMovementService.saveStockMovement(stockMovement);
          orderStockMovementRepository.save(new OrderStockMovement(order, stockMovement, stockMovementQuantity));
          order.setFulfilled(true);

          return true;
        } else {
          orderQuantity -= stockMovementQuantity;
          stockMovement.setQuantity(0);
          stockMovementService.saveStockMovement(stockMovement);
          orderStockMovementRepository.save(new OrderStockMovement(order, stockMovement, stockMovementQuantity));
        }
      }
    }

    return false;
  }

  @Override
  public Optional<Order> getOrderById(Long id) {
    return orderRepository.findById(id);
  }

  @Override
  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  @Override
  public List<Order> getUnfilledOrdersByItem(Item item) {
    return orderRepository.findUnfilledOrdersByItem(item, Sort.by(Direction.DESC, "quantity"));
  }

  @Override
  public void deleteOrder(Long id) {
    orderRepository.deleteById(id);
  }
}