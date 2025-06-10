package com.rtech.jewellery.controller;

import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/saveProduct")
    public ResponseEntity<String> saveProduct(@RequestBody Product product){

        Optional<Product> existingProduct = purchaseService.findProduct(product.getType(),product.getGram());

        if(existingProduct.isPresent()){
            Product productObj = existingProduct.get();

            //update active checkbox
            productObj.setIsActive(false);
            purchaseService.saveProduct(productObj);

            //insert product
            int qty = productObj.getRemainingQty()+product.getQuantity();
            product.setQuantity(qty);
            product.setRemainingQty(qty);
            purchaseService.saveProduct(product);

          return ResponseEntity.ok("Product updated.");
        }else {
            product.setRemainingQty(product.getQuantity());
            purchaseService.saveProduct(product);
            return ResponseEntity.ok("Product saved successfully.");
        }
    }

    @GetMapping("/purchaseHistory")
    public List<Product> getAllProduct(){
        return purchaseService.getFindAll();
    }
}
