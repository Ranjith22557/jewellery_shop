package com.rtech.jewellery.service;

import com.rtech.jewellery.entity.Sales;
import com.rtech.jewellery.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public List<Sales> getAllPendingPayment(){
        return paymentRepository.getAllPendingPayment();
    }
}
