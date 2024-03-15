package com.sibs.ordermanager.service.interfaces;

import com.sibs.ordermanager.model.Item;
import com.sibs.ordermanager.model.Order;
import com.sibs.ordermanager.model.StockMovement;
import java.util.List;
import java.util.Optional;

public interface OrderService {

  Order saveOrder(Order order);

  Optional<Order> getOrderById(Long id);

  List<Order> getAllOrders();

  List<Order> getUnfilledOrdersByItem(Item item);

  void deleteOrder(Long id);

  boolean tryFulfillOrder(Order order, List<StockMovement> stockMovements);
}
