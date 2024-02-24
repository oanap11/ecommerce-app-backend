package com.ecommerce.store.service;

import com.ecommerce.store.dao.CustomerRepository;
import com.ecommerce.store.dto.Purchase;
import com.ecommerce.store.dto.PurchaseResponse;
import com.ecommerce.store.entity.Customer;
import com.ecommerce.store.entity.Order;
import com.ecommerce.store.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CustomerRepository customerRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        Order order = purchase.getOrder();
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        Set<OrderItem> orderItems = purchase.getOrderItems();
        orderItems.forEach(item -> order.add(item));

        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        Customer customer = purchase.getCustomer();
        customer.addOrder(order);

        customerRepository.save(customer);
        return new PurchaseResponse(orderTrackingNumber);
    }

    public String generateOrderTrackingNumber() {
        return UUID.randomUUID().toString();
    }
}
