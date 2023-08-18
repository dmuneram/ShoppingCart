package com.xcale.cart.scheduler;

import com.xcale.cart.service.CartService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SchedulerTest {

    @Mock
    private CartService service;

    @InjectMocks
    private Scheduler scheduler;

    @Test
    void scheduleTask() {
        scheduler.scheduleTask();

        verify(service, times(1)).deleteOldCarts();
    }
}