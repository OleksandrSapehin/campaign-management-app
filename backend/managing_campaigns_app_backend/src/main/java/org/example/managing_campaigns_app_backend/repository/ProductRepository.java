package org.example.managing_campaigns_app_backend.repository;

import org.example.managing_campaigns_app_backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
