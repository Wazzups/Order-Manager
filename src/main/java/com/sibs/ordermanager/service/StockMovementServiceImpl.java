package com.sibs.ordermanager.service;

import com.sibs.ordermanager.model.Item;
import com.sibs.ordermanager.model.Order;
import com.sibs.ordermanager.model.StockMovement;
import com.sibs.ordermanager.repository.StockMovementRepository;
import com.sibs.ordermanager.service.interfaces.OrderService;
import com.sibs.ordermanager.service.interfaces.StockMovementService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StockMovementServiceImpl implements StockMovementService {

  private final OrderService orderService;
  private final StockMovementRepository stockMovementRepository;

  @Autowired
  public StockMovementServiceImpl(OrderService orderService,
      StockMovementRepository stockMovementRepository) {
    this.orderService = orderService;
    this.stockMovementRepository = stockMovementRepository;
  }

  @Override
  public StockMovement saveStockMovement(StockMovement stockMovement) {
    StockMovement savedStockMovement = stockMovementRepository.save(stockMovement);
    log.info("Stock movement created with success: {}", savedStockMovement);

    checkAndFulfillOrders(savedStockMovement);

    return savedStockMovement;
  }

  private void checkAndFulfillOrders(StockMovement stockMovement) {
    List<Order> unfilledOrders = orderService.getUnfilledOrdersByItem(stockMovement.getItem());
    for (Order order : unfilledOrders) {
      tryFulfillOrderWithCurrentStock(order, stockMovement);
    }
  }

  private void tryFulfillOrderWithCurrentStock(Order order, StockMovement stockMovement) {
    orderService.tryFulfillOrder(order, Collections.singletonList(stockMovement));
  }

  @Override
  public Optional<StockMovement> getStockMovementById(Long id) {
    return stockMovementRepository.findById(id);
  }

  @Override
  public List<StockMovement> getAllStockMovements() {
    return stockMovementRepository.findAll();
  }

  @Override
  public void deleteStockMovement(Long id) {
    stockMovementRepository.deleteById(id);
  }

  public List<StockMovement> getStockMovementByItem(Item item, Sort sort) {
    return stockMovementRepository.findByItem(item, sort);
  }
}