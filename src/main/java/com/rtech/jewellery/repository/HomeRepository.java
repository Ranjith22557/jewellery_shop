package com.rtech.jewellery.repository;

import com.rtech.jewellery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeRepository extends JpaRepository<Product,Long> {

    @Query(value = "select p.type,p.quantity,p.gram  from product p where p.is_active=true ",nativeQuery = true)
    List<Object[]> fetchActiveProductCountsByType();
}
