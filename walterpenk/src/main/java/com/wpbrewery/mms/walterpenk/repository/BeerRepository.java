package com.wpbrewery.mms.walterpenk.repository;

import com.wpbrewery.mms.walterpenk.entity.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BeerRepository extends JpaRepository<Beer, UUID> {
}
