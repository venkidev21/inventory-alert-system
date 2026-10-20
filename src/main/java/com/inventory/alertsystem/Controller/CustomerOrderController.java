package com.inventory.alertsystem.Controller;


import com.inventory.alertsystem.Entity.CustomerOrder;
import com.inventory.alertsystem.Service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final CustomerOrderService orderService;

    @PostMapping
    public ResponseEntity<CustomerOrder> createOrder(
            @RequestBody CustomerOrder order) {

        CustomerOrder savedOrder =
                orderService.createOrder(order);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedOrder);
    }

    @GetMapping
    public ResponseEntity<List<CustomerOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
