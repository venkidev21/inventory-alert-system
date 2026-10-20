
package com.inventory.alertsystem.consumer;

import com.inventory.alertsystem.Entity.Product;
import com.inventory.alertsystem.Entity.LowStockAlert;
import com.inventory.alertsystem.Repository.ProductRepository;
import com.inventory.alertsystem.Repository.LowStockAlertRepository;
import com.inventory.alertsystem.event.OrderCreated;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final ProductRepository productRepository;
    private final LowStockAlertRepository alertRepository;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "order.created.queue")
    public void receiveOrderEvent(String message) {

        try {
            OrderCreated event =
                    objectMapper.readValue(message, OrderCreated.class);

            Product product = productRepository
                    .findById(event.getProductId())
                    .orElseThrow(() -> new RuntimeException(
                            "Product not found: " + event.getProductId()
                    ));

            int orderedQuantity = event.getQuantity();

            if (orderedQuantity <= 0) {
                throw new IllegalArgumentException(
                        "Order quantity must be greater than zero"
                );
            }

            if (product.getStockQuantity() < orderedQuantity) {
                System.err.println(
                        "Order rejected: insufficient stock. Product ID: "
                                + product.getId()
                                + ", Available: " + product.getStockQuantity()
                                + ", Requested: " + orderedQuantity
                );
                return;
            }

            // 1. Reduce product stock
            int remainingStock =
                    product.getStockQuantity() - orderedQuantity;

            product.setStockQuantity(remainingStock);
            productRepository.save(product);

            System.out.println("Order Event Processed!");
            System.out.println("Remaining stock: " + remainingStock);

            // 2. Save alert when stock reaches threshold
            if (remainingStock <= product.getLowStockThreshold()) {

                var existingAlert = alertRepository
                        .findFirstByProductIdAndStatusOrderByCreatedAtDesc(
                                product.getId(), "PENDING");

                if (existingAlert.isPresent()) {

                    LowStockAlert alert = existingAlert.get();
                    alert.setCurrentStock(remainingStock);
                    alert.setThreshold(product.getLowStockThreshold());

                    alertRepository.save(alert);

                    System.out.println("Existing low-stock alert updated!");

                } else {

                    LowStockAlert alert = new LowStockAlert();

                    alert.setProductId(product.getId());
                    alert.setProductName(product.getName());
                    alert.setCurrentStock(remainingStock);
                    alert.setThreshold(product.getLowStockThreshold());
                    alert.setStatus("PENDING");

                    alertRepository.save(alert);

                    System.out.println("New low-stock alert created!");
                }
            }

        } catch (Exception e) {
            System.err.println(
                    "Failed to process order event: " + e.getMessage()
            );
            throw new RuntimeException(e);
        }
    }
}
