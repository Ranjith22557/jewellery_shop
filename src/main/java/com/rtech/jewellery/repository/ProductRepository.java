package com.rtech.jewellery.repository;

import com.rtech.jewellery.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    Optional<Product> findByTypeAndGram(String type, String gram);

    @Query(value = "select * from Product where quantity > 0 and remaining_qty >0 and is_active=true",nativeQuery = true)
    public List<Product> getAllRemainingQtyProduct();

}
