package com.lpnu.excursionclient.repository.order;

import com.lpnu.excursionclient.model.client.Client;
import com.lpnu.excursionclient.model.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findAllByClient(Client client, Pageable pageable);
}
