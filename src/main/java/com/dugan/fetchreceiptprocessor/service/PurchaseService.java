package com.dugan.fetchreceiptprocessor.service;

import com.dugan.fetchreceiptprocessor.aop.PointsNotFoundException;
import com.dugan.fetchreceiptprocessor.entity.Item;
import com.dugan.fetchreceiptprocessor.entity.Purchase;
import com.dugan.fetchreceiptprocessor.jpa.PurchaseRepository;
import com.dugan.fetchreceiptprocessor.mapper.PurchaseMapper;
import com.dugan.fetchreceiptprocessor.web.dto.ProcessRequest;
import com.dugan.fetchreceiptprocessor.web.dto.ReceiptPointResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final PointCalculatorService pointCalculatorService;

    public PurchaseService(PurchaseRepository purchaseRepository,
                           PointCalculatorService pointCalculatorService){
        this.purchaseRepository = purchaseRepository;
        this.pointCalculatorService = pointCalculatorService;
    }

    public Purchase processReceipt(ProcessRequest processRequest){
        Purchase purchase = PurchaseMapper.fromProcessRequest(processRequest);
        applyPointRulesToPurchaseReceipt(purchase);
        purchaseRepository.save(purchase);
        return purchase;
    }

    public ReceiptPointResponse getPointsByPurchaseId(UUID id){
        return purchaseRepository.findPurchaseById(id).orElseThrow(PointsNotFoundException::new);
    }

    private void applyPointRulesToPurchaseReceipt(Purchase purchase){
        Integer pointsTotal = 0;

        pointsTotal += pointCalculatorService.alphaNumericRetailerRule(purchase.getRetailer());
        pointsTotal += pointCalculatorService.roundDollarAmountRule(purchase.getTotal());
        pointsTotal += pointCalculatorService.multipleOfAQuarterRule(purchase.getTotal());
        pointsTotal += pointCalculatorService.fiveForEveryTwoItemsRule(purchase.getItems().size());
        for(Item item : purchase.getItems()){
            pointsTotal += pointCalculatorService.descriptionMultipleOfThreeRule(item.getShortDescription(), item.getPrice());
        }
        pointsTotal += pointCalculatorService.sixPointsForOddDayRule(purchase.getPurchaseDate());
        pointsTotal += pointCalculatorService.timeBetweenTwoAndFourPMRule(purchase.getPurchaseTime());

        purchase.setPoints(pointsTotal);
    }
}
