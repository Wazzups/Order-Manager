package com.sibs.ordermanager.service.interfaces;

import com.sibs.ordermanager.model.Item;
import com.sibs.ordermanager.model.StockMovement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Sort;

public interface StockMovementService {

  StockMovement saveStockMovement(StockMovement stockMovement);

  Optional<StockMovement> getStockMovementById(Long id);

  List<StockMovement> getAllStockMovements();

  void deleteStockMovement(Long id);

  List<StockMovement> getStockMovementByItem(Item item, Sort sort);
}