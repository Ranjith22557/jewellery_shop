package com.rtech.jewellery.controller;

import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.entity.Sales;
import com.rtech.jewellery.service.PurchaseService;
import com.rtech.jewellery.service.SaleService;
import com.rtech.jewellery.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    SaleService saleService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    private SmsService smsService;

    @GetMapping("/totalAmount")
    public ResponseEntity<Map<String,Object>> getPriceByType(@RequestParam String type,@RequestParam String gram){

        Object result =  saleService.getPriceByTypeAndGram(type,gram);
        Map<String, Object> response = new HashMap<>();
        if(result != null){
            Object[] arr = (Object[]) result;
            BigDecimal totalAmount = (BigDecimal) arr[0];
            Integer qty = ((Number) arr[1]).intValue();
            response.put("totalAmount",totalAmount);
            response.put("qty",qty);
        }else{
            response.put("totalAmount",BigDecimal.ZERO);
            response.put("qty",0);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveCustomer")
    public ResponseEntity<String> saveCustomer(@RequestBody Sales sales){

        //update quantity in product table
        Product productObj = saleService.findProduct(sales.getItemType(),sales.getGram());

        if(productObj != null){

            int qty = productObj.getQuantity() - sales.getQuantity();
            productObj.setQuantity(qty);
            productObj.setRemainingQty(qty);
            purchaseService.saveProduct(productObj);
        }
        //Save customer
        saleService.processSales(sales);
        return ResponseEntity.ok().body("Customer saved successfully");
    }

    @GetMapping("/salesList")
    public List<Sales> getAllSales(){
        return saleService.findAll();
    }
}
