package com.rtech.jewellery.controller;

import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    PurchaseService purchaseService;

    @GetMapping("/stockList")
    public List<Product> getAllRemainingQtyProduct(){
        return purchaseService.getAllRemainingQtyProduct();
    }
}
