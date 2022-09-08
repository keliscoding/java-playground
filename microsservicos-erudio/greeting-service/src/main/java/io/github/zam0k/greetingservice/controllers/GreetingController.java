package io.github.zam0k.greetingservice.controllers;

import java.util.concurrent.atomic.AtomicLong;

import io.github.zam0k.greetingservice.configs.GreetingConfig;
import io.github.zam0k.greetingservice.model.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String TEMPLATE = "%s, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private GreetingConfig config;

    @RequestMapping("/greeting")
    public Greeting greeting(
            @RequestParam(value = "name",
                    defaultValue = "") String name) {

        if (name.isBlank())
            name = config.getDefaultValue();


        return new Greeting(
                counter.incrementAndGet(),
                String.format(TEMPLATE, config.getGreeting(), name)
        );
    }
}
