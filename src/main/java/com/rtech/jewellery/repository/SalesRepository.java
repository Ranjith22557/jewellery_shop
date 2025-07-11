package com.rtech.jewellery.repository;

import com.rtech.jewellery.entity.Product;
import com.rtech.jewellery.entity.Sales;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SalesRepository extends JpaRepository<Sales,Long> {

    //get totalAmount
    @Query(value = "select total_amount,remaining_qty from product where type = :type and gram = :gram and isActive =true ",nativeQuery = true)
    Object findPriceByTypeAndGram(@Param("type")String type,@Param("gram") String gram);

    //update quantity
    @Query(value = "select * from product p where p.type = :type and p.gram = :gram and p.isActive =true ",nativeQuery = true)
    Product findProduct(@Param("type")String type, @Param("gram") String gram);

    @Query(value = "SELECT EXTRACT(month from s.date) AS month, EXTRACT(year from  s.date) AS year, SUM(s.net_Total_Amount) AS totalAmount " +
            " FROM Sales s GROUP BY EXTRACT(year from s.date), EXTRACT(month from s.date) ORDER BY year, month",nativeQuery = true)
    List<Object[]> getMonthlySalesSummary();

}
