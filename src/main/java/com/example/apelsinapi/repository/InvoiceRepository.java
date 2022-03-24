package com.example.apelsinapi.repository;

import com.example.apelsinapi.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
    List<Invoice> findAllByOrder_Id(Integer id);

    @Query(value = "select * from invoice i where  i.issued > i.due", nativeQuery = true)
    List<Invoice> getExpiredInvoices();

    @Query(value = "select * from invoice i inner join orders o on o.id = i.order_id where i.issued < o.date",
            nativeQuery = true)
    List<Invoice> getWrongInvoices();

    @Query(value = "select * from invoice i join payment p on i.id = p.invoice_id where i.amount < (select sum(p.amount) from payment p where p.invoice_id = i.id group by p.invoice_id)", nativeQuery = true)
    List<Invoice> getOverPaidInvoices();
}
