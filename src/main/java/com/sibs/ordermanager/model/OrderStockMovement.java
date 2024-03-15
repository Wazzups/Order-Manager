package com.sibs.ordermanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderStockMovement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Order order;

  @ManyToOne
  private StockMovement stockMovement;

  private long quantityUsed;

  public OrderStockMovement(Order order, StockMovement stockMovement, long quantityUsed) {
    this.order = order;
    this.stockMovement = stockMovement;
    this.quantityUsed = quantityUsed;
  }
}