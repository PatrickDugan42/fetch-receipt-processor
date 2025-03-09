package com.dugan.fetchreceiptprocessor.mapper;

import com.dugan.fetchreceiptprocessor.entity.Item;
import com.dugan.fetchreceiptprocessor.entity.Purchase;
import com.dugan.fetchreceiptprocessor.web.dto.ProcessRequest;

import java.math.BigDecimal;
import java.util.List;

public class PurchaseMapper {

    public static Purchase fromProcessRequest(ProcessRequest processRequest){
        Purchase purchase = new Purchase();
        purchase.setRetailer(processRequest.retailer());
        purchase.setPurchaseDate(processRequest.purchaseDate());
        purchase.setPurchaseTime(processRequest.purchaseTime());
        purchase.setTotal(new BigDecimal(processRequest.total()));

        List<Item> items = processRequest.items().stream()
                .map(processItem ->
                        new Item(null, purchase, processItem.shortDescription(), processItem.price()))
                .toList();

        purchase.setItems(items);

        return purchase;
    }
}
