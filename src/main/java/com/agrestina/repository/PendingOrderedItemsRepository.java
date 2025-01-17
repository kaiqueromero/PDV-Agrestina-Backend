package com.agrestina.repository;

import com.agrestina.domain.orderedItems.PendingOrderedItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PendingOrderedItemsRepository extends JpaRepository<PendingOrderedItem, Long> {
}