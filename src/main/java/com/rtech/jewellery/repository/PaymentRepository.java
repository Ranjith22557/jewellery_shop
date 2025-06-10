package com.rtech.jewellery.repository;

import com.rtech.jewellery.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Sales, Long> {

    @Query(value = "select * from sales where pending_amount > 0",nativeQuery = true)
    public List<Sales> getAllPendingPayment();
}
