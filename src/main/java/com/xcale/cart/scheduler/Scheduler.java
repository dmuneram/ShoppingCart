package com.xcale.cart.scheduler;

import com.xcale.cart.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler class every minute will call delete old carts.
 */
@Component
public class Scheduler {

    private final Logger logger = LoggerFactory.getLogger(Scheduler.class);

    private final CartService service;

    public Scheduler(CartService service) {
        this.service = service;
    }

    /**
     * This method will execute every minute
     */
    @Scheduled(cron = "0 * * * * ?")
    public void scheduleTask() {
        logger.info("EXECUTING CRON");
        service.deleteOldCarts();
    }
}
