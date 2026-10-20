
package com.inventory.alertsystem.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderCreated {
    private Long orderId;
    private Long productId;
    private int quantity;
}
