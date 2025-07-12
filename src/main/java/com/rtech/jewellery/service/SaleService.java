package com.rtech.jewellery.service;

import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.entity.Sales;
import com.rtech.jewellery.repository.SalesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleService {

    @Autowired
    SalesRepository salesRepository;

    @Autowired
    SmsService smsService;

    //get price based on type and gram
    public Object getPriceByTypeAndGram(String type,String gram){
        return salesRepository.findPriceByTypeAndGram(type,gram);
    }

    //Save customer
    public Sales saveCustomer(Sales sales){
       return salesRepository.save(sales);
    }

    //update quantity
    public Product findProduct(String type, String gram){
        return salesRepository.findProduct(type,gram);
    }

    //get all Sales List
    public List<Sales> findAll(){
        return salesRepository.findAll();
    }

    @Transactional
    public void processSales(Sales sales){

        salesRepository.save(sales);

        String message = buildMessage(sales);
        smsService.sendSms(sales.getPhoneNo(),message);
    }

    private String buildMessage(Sales sales){
        return "Murugan Jeweller\n" +
                "Customer Name: " + sales.getCustomerName() + "\n" +
                "Date: " + sales.getDate() + "\n" +
                "Product Name: " + sales.getItemType() + "\n" +
                "Gram: " + sales.getGram() + "\n" +
                "Amount: ₹" + sales.getNetTotalAmount();
    }
}
