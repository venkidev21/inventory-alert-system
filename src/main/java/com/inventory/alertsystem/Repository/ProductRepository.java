package com.inventory.alertsystem.Repository;

import com.inventory.alertsystem.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public interface ProductRepository
        extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);

    @Query("SELECT p FROM Product p "
            + "WHERE p.stockQuantity <= p.lowStockThreshold")
    List<Product> findLowStockProducts();
}


//package com.inventory.alertsystem.repository;
//
//import com.inventory.alertsystem.entity.Product;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.List;
//import java.util.Optional;

