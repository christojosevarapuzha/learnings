package com.learn.webservices.webservices.controller;

import com.learn.webservices.webservices.bean.LimitConfiguration;
import com.learn.webservices.webservices.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimitsController {

    @Autowired
    private Configuration configuration;

    @GetMapping("/limits")
    public LimitConfiguration retrieveLimitsFromConfigurations() {
        LimitConfiguration limitConfiguration = new LimitConfiguration(configuration.getMaximum(),
                configuration.getMinimum());
        return limitConfiguration;
    }
}
