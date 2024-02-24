package com.ecommerce.store.service;

import static org.junit.Assert.*;
import java.util.UUID;

import com.ecommerce.store.dao.CustomerRepository;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CheckoutServiceImplTest {

    private CustomerRepository customerRepository;

    @Test
    public void testGenerateOrderTrackingNumber() {
        // Arrange
        CheckoutServiceImpl checkoutService = new CheckoutServiceImpl(customerRepository);

        // Act
        String trackingNumber = checkoutService.generateOrderTrackingNumber();

        // Assert
        assertNotNull("Tracking number should not be null", trackingNumber);
        assertTrue("Generated tracking number should be a valid UUID", isValidUUID(trackingNumber));
    }

    private boolean isValidUUID(String uuid) {
        try {
            UUID.fromString(uuid);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
