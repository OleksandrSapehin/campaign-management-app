package org.example.managing_campaigns_app_backend.repository;

import org.example.managing_campaigns_app_backend.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller,Long> {

}
