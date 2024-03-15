package com.sibs.ordermanager.repository;

import com.sibs.ordermanager.model.Item;
import com.sibs.ordermanager.model.Order;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findUnfilledOrdersByItem(Item item, Sort sort);

}
