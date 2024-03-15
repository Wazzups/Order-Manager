package com.sibs.ordermanager.repository;

import com.sibs.ordermanager.model.OrderStockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStockMovementRepository extends JpaRepository<OrderStockMovement, Long> {}
