package com.sibs.ordermanager.controller;

import com.sibs.ordermanager.model.StockMovement;
import com.sibs.ordermanager.service.interfaces.StockMovementService;
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
@RequestMapping("/api/stockmovements")
public class StockMovementController {

  private final StockMovementService stockMovementService;

  @Autowired
  public StockMovementController(StockMovementService stockMovementService) {
    this.stockMovementService = stockMovementService;
  }

  @PostMapping
  public ResponseEntity<StockMovement> createStockMovement(
      @RequestBody StockMovement stockMovement) {
    StockMovement savedStockMovement = stockMovementService.saveStockMovement(stockMovement);
    return new ResponseEntity<>(savedStockMovement, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public ResponseEntity<StockMovement> getStockMovementById(@PathVariable Long id) {
    return stockMovementService.getStockMovementById(id).map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<StockMovement>> getAllStockMovements() {
    List<StockMovement> stockMovements = stockMovementService.getAllStockMovements();
    return new ResponseEntity<>(stockMovements, HttpStatus.OK);
  }

  @PutMapping("/{id}")
  public ResponseEntity<StockMovement> updateStockMovement(@PathVariable Long id,
      @RequestBody StockMovement stockMovementDetails) {
    return stockMovementService.getStockMovementById(id).map(stockMovement -> {
      stockMovement.setItem(stockMovementDetails.getItem());
      stockMovement.setQuantity(stockMovementDetails.getQuantity());

      StockMovement updatedStockMovement = stockMovementService.saveStockMovement(stockMovement);
      return new ResponseEntity<>(updatedStockMovement, HttpStatus.OK);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteStockMovement(@PathVariable Long id) {
    return stockMovementService.getStockMovementById(id).map(stockMovement -> {
      stockMovementService.deleteStockMovement(id);
      return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }).orElseGet(() -> ResponseEntity.notFound().build());
  }
}
