package com.rtech.jewellery.service;

import com.rtech.jewellery.dto.MonthlySalesDto;
import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.entity.Sales;
import com.rtech.jewellery.repository.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    //Get sales Customer
    public Optional<Sales> getSalesById(Long id){
        return salesRepository.findById(id);
    }

    //Get Monthly sales List
    public List<MonthlySalesDto> getMonthlySalesList(){
        List<Object[]> results = salesRepository.getMonthlySalesSummary();
        List<MonthlySalesDto> summaryList = new ArrayList<>();

        for (Object[] row:results){
            int month = ((BigDecimal) row[0]).intValue();
            int year = ((BigDecimal) row[1]).intValue();
            BigDecimal total = (BigDecimal) row[2];

            summaryList.add(new MonthlySalesDto(month,year,total));
        }
        return summaryList;
    }
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
