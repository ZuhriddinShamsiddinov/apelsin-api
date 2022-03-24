package com.example.apelsinapi.repository;

import com.example.apelsinapi.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DetailRepository extends JpaRepository<Detail, UUID> {
    List<Detail> findAllByProduct_Id(Integer id);

    @Query(value = "select * from detail d join orders o on o.id = d.order_id join product p on p.id = d.product_id", nativeQuery = true)
    List<Detail> getGeneralOrders();
}
