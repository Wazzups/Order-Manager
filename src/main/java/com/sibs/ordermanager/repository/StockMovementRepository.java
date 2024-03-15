package com.sibs.ordermanager.repository;

import com.sibs.ordermanager.model.Item;
import com.sibs.ordermanager.model.StockMovement;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

  List<StockMovement> findByItem(Item item, Sort sort);
}
