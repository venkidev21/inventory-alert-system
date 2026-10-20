package com.inventory.alertsystem.Repository;

import com.inventory.alertsystem.Entity.LowStockAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LowStockAlertRepository
        extends JpaRepository<LowStockAlert, Long> {

    Optional<LowStockAlert> findFirstByProductIdAndStatusOrderByCreatedAtDesc(
            Long productId, String status);
}