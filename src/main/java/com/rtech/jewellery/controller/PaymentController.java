package com.rtech.jewellery.controller;

import com.rtech.jewellery.entity.Sales;
import com.rtech.jewellery.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @GetMapping("/paymentList")
    public List<Sales> getAllPendingPayment(){
        return paymentService.getAllPendingPayment();
    }
}
