package com.cesco.api.cesnetapi.cm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HealthCheck {
    
    @GetMapping("/health")
    @ResponseBody
    public Boolean health() {
        return true;
    }

    @GetMapping("/version")
    @ResponseBody
    public String version() {
        return "beta.3";
    }
}
