package com.dugan.fetchreceiptprocessor.jpa;

import com.dugan.fetchreceiptprocessor.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {
}
