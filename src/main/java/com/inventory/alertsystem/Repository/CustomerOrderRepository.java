package com.inventory.alertsystem.Repository;



import com.inventory.alertsystem.Entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository
        extends JpaRepository<CustomerOrder, Long> {
}
