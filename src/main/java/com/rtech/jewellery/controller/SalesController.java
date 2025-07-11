package com.rtech.jewellery.controller;

import com.rtech.jewellery.dto.MonthlySalesDto;
import com.rtech.jewellery.dto.SalesUpdateDTO;
import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.entity.Sales;
import com.rtech.jewellery.service.EmailService;
import com.rtech.jewellery.service.PdfGeneratorService;
import com.rtech.jewellery.service.PurchaseService;
import com.rtech.jewellery.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/sales")
public class SalesController {

    @Autowired
    SaleService saleService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    PdfGeneratorService pdfGeneratorService;

    @Autowired
    EmailService emailService;

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
        saleService.saveCustomer(sales);

        try{
            String name = sales.getCustomerName();
            String itemType = sales.getItemType();
            BigDecimal totalAmount = sales.getNetTotalAmount();
            String email = sales.getEmail();

            byte[] pdf = pdfGeneratorService.generatePdf(name,itemType,totalAmount);

            emailService.sendPdfEmail(email, name, pdf);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Customer saved, but failed to send email.");
        }
        return ResponseEntity.ok().body("Customer saved successfully");
    }

    @GetMapping("/salesList")
    public List<Sales> getAllSales(){
        return saleService.findAll();
    }

    @GetMapping("/print/{id}")
    public ResponseEntity<byte[]> printSalesPdf(@PathVariable Long id){

        try{
            Optional<Sales> sales = saleService.getSalesById(id);

                Sales salesObj = sales.get();
                String name = salesObj.getCustomerName();
                String itemType = salesObj.getItemType();
                BigDecimal totalAmount = salesObj.getNetTotalAmount();
                byte[] pdf = pdfGeneratorService.generatePdf(name,itemType,totalAmount);

                String fileName = "receipt_"+name.replace("[^a-zA-Z0-9]", "_")+".pdf";

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", fileName);

            return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/monthlySales")
    public ResponseEntity<List<MonthlySalesDto>> getMonthlySales(){
        List<MonthlySalesDto> monthlySalesList = saleService.getMonthlySalesList();

        if(monthlySalesList.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(monthlySalesList);
    }

    @PostMapping("edit")
    public ResponseEntity<String> updateAmount(@RequestBody SalesUpdateDTO dto){

        Optional<Sales> sales =  saleService.getSalesById(dto.id());
        if(sales.isPresent()){
            Sales salesObj = sales.get();
            BigDecimal paidAmount = salesObj.getPaidAmount().add(dto.pendingAmount());
            salesObj.setPendingAmount(dto.pendingAmount());
            salesObj.setPaidAmount(paidAmount);
            saleService.saveCustomer(salesObj);
        }
        return ResponseEntity.ok("Updated successfully");
    }
}
