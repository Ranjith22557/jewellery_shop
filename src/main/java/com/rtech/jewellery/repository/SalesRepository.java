package com.rtech.jewellery.repository;

import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SalesRepository extends JpaRepository<Sales,Long> {

    //get totalAmount
    @Query(value = "select total_amount,remaining_qty from product where type = :type and gram = :gram and isActive =true ",nativeQuery = true)
    Object findPriceByTypeAndGram(@Param("type")String type,@Param("gram") String gram);

    //update quantity
    @Query(value = "select * from product p where p.type = :type and p.gram = :gram and p.isActive =true ",nativeQuery = true)
    Product findProduct(@Param("type")String type, @Param("gram") String gram);
}
