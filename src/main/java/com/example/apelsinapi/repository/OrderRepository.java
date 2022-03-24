package com.example.apelsinapi.repository;

import com.example.apelsinapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByActiveTrue();

    List<Order> findAllByCustomer_Id(Integer id);

    @Query(value = "select * from orders where id not in (select order_id from detail)", nativeQuery = true)
    List<Order> getNoDetail();

    @Query(value = "select * from orders o join customer c on c.id = o.customer_id where o.date in (select max(date) from orders o where o.customer_id = c.id group by o.customer_id )", nativeQuery = true)
    List<Order> getLastCustomerOrders();
}
