package com.wpbrewery.mms.walterpenk.repository;


import com.wpbrewery.mms.walterpenk.entity.BeerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeerOrderRepository extends JpaRepository<BeerOrder, UUID> {
}
