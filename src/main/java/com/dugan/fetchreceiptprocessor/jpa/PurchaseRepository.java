package com.dugan.fetchreceiptprocessor.jpa;

import com.dugan.fetchreceiptprocessor.entity.Purchase;
import com.dugan.fetchreceiptprocessor.web.dto.ReceiptPointResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PurchaseRepository extends JpaRepository<Purchase, UUID> {

    Optional<ReceiptPointResponse> findPurchaseById(UUID id);
}
