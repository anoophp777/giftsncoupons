package com.giftsncoupons.cart.controller;

import com.giftsncoupons.cart.application.services.CounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counter")
public class CounterController {

    private CounterService counterService;

    @Autowired
    public CounterController(CounterService counterService) {
        this.counterService = counterService;
    }

    @GetMapping("/increment")
    public int incrementCounter() {
        return counterService.incrementCounter();
    }

    @GetMapping
    public int getCounter() {
        return counterService.getCounter();
    }

    @PostMapping("/reset")
    public void resetCounter() {
        counterService.resetCounter();
    }
}
