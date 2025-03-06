package com.dugan.fetchreceiptprocessor;

import com.dugan.fetchreceiptprocessor.entity.Item;
import com.dugan.fetchreceiptprocessor.entity.Purchase;
import com.dugan.fetchreceiptprocessor.service.PurchaseService;
import com.dugan.fetchreceiptprocessor.web.dto.ProcessItem;
import com.dugan.fetchreceiptprocessor.web.dto.ProcessRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PurchaseServiceTest {

    @Autowired
    PurchaseService uut;

    @Test
    void processPurchaseReceipt(){

        //example api one
        List<ProcessItem> items = new ArrayList<>();
        items.add(new ProcessItem("Mountain Dew 12PK", new BigDecimal("6.49")));
        items.add(new ProcessItem("Emils Cheese Pizza", new BigDecimal("12.25")));
        items.add(new ProcessItem("Knorr Creamy Chicken", new BigDecimal("1.26")));
        items.add(new ProcessItem("Doritos Nacho Cheese", new BigDecimal("3.35")));
        items.add(new ProcessItem("   Klarbrunn 12-PK 12 FL OZ  ", new BigDecimal("12.00")));

        ProcessRequest receipt = new ProcessRequest("Target", "2022-01-01", "13:01", items, "35.55");
        Purchase purchase = uut.processReceipt(receipt);
        assertEquals(28, purchase.getPoints());

        //example api 2
        items.clear();
        items.add(new ProcessItem("Gatorade", new BigDecimal("2.25")));
        items.add(new ProcessItem("Gatorade", new BigDecimal("2.25")));
        items.add(new ProcessItem("Gatorade", new BigDecimal("2.25")));
        items.add(new ProcessItem("Gatorade", new BigDecimal("2.25")));

        receipt = new ProcessRequest("M&M Corner Market", "2022-03-20", "14:33", items, "9.00");
        purchase = uut.processReceipt(receipt);
        assertEquals(109, purchase.getPoints());
    }
}
