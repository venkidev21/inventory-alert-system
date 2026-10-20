package com.inventory.alertsystem.Service;

import com.inventory.alertsystem.Entity.CustomerOrder;
import com.inventory.alertsystem.Repository.CustomerOrderRepository;
import com.inventory.alertsystem.event.OrderCreated;
import com.inventory.alertsystem.Service.OrderEventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerOrderService {

    private final CustomerOrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    public CustomerOrder createOrder(CustomerOrder order) {
        order.setStatus("PENDING");

        CustomerOrder savedOrder = orderRepository.save(order);

        OrderCreated event = new OrderCreated(
                savedOrder.getId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity()
        );

        orderEventPublisher.publishOrderCreated(event);

        return savedOrder;
    }

    public List<CustomerOrder> getAllOrders() {
        return orderRepository.findAll();
    }
}