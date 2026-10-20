
package com.inventory.alertsystem.Service;

import com.inventory.alertsystem.event.OrderCreated;
import tools.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void publishOrderCreated(OrderCreated event) {
        try {
            String message = objectMapper.writeValueAsString(event);

            rabbitTemplate.convertAndSend(
                    "order.created.queue",
                    message
            );

            System.out.println("Order event published: " + message);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to publish order event", e
            );
        }
    }
}
