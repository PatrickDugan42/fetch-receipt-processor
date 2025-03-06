package com.dugan.fetchreceiptprocessor.web.rest;

import com.dugan.fetchreceiptprocessor.entity.Purchase;
import com.dugan.fetchreceiptprocessor.service.PurchaseService;
import com.dugan.fetchreceiptprocessor.web.dto.ProcessRequest;
import com.dugan.fetchreceiptprocessor.web.dto.ProcessResponse;
import com.dugan.fetchreceiptprocessor.web.dto.ReceiptPointResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@RestController
public class PurchaseController {

    private PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService){
        this.purchaseService = purchaseService;
    }

    @PostMapping(
            path = "/receipts/process",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProcessResponse> processPurchase(@Valid @RequestBody ProcessRequest processRequest){
        Purchase purchase = purchaseService.processReceipt(processRequest);
        ProcessResponse response = new ProcessResponse(purchase.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }

    @GetMapping(
            path = "/receipts/{id}/points"
    )
    public ResponseEntity<ReceiptPointResponse> getPointsById(@PathVariable UUID id){
        ReceiptPointResponse response = purchaseService.getPointsByPurchaseId(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(response);
    }
}
