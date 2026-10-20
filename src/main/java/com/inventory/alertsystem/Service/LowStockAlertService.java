
package com.inventory.alertsystem.Service;

import com.inventory.alertsystem.Entity.LowStockAlert;
import com.inventory.alertsystem.Repository.LowStockAlertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LowStockAlertService {

    private final LowStockAlertRepository alertRepository;

    public List<LowStockAlert> getAllAlerts() {
        return alertRepository.findAll();
    }

    public List<LowStockAlert> getAlertsByStatus(String status) {
        return alertRepository.findAll()
                .stream()
                .filter(alert -> alert.getStatus().equalsIgnoreCase(status))
                .toList();
    }
    public LowStockAlert updateAlertStatus(Long id, String status) {

        LowStockAlert alert = alertRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Alert not found: " + id));

        if (!status.equals("PENDING")
                && !status.equals("RESTOCKED")
                && !status.equals("URGENT")) {
            throw new IllegalArgumentException("Invalid alert status");
        }

        alert.setStatus(status);
        return alertRepository.save(alert);
    }
}
