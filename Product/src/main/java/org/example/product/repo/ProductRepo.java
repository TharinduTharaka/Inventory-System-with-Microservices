package org.example.product.repo;


import org.example.product.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Integer> {
    @Query(value = "SELECT * FROM products WHERE id = ?1", nativeQuery = true)
    ProductEntity getProductById(Long productId);
}
