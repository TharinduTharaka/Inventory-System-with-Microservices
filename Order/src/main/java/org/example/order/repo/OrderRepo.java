package org.example.order.repo;


import org.example.order.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity, Integer> {
    @Query(value = "SELECT * FROM order WHERE id = ?1", nativeQuery = true)
    OrderEntity getOrderById(Integer orderId);
}
