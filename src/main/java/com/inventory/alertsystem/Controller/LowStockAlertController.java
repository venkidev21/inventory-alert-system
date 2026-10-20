
package com.inventory.alertsystem.Controller;

import com.inventory.alertsystem.Entity.LowStockAlert;
import com.inventory.alertsystem.Service.LowStockAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
@RequiredArgsConstructor
public class LowStockAlertController {

    private final LowStockAlertService alertService;

    @GetMapping
    public List<LowStockAlert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/status/{status}")
    public List<LowStockAlert> getAlertsByStatus(
            @PathVariable String status) {
        return alertService.getAlertsByStatus(status);
    }


    @PatchMapping("/{id}/status")
    public LowStockAlert updateAlertStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        return alertService.updateAlertStatus(
                id,
                status.toUpperCase()
        );
    }

}
