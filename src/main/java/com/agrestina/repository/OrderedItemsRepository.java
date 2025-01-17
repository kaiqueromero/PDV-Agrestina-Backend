package com.agrestina.repository;

import com.agrestina.domain.orderedItems.OrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedItemsRepository extends JpaRepository<OrderedItem, Long> {
}
