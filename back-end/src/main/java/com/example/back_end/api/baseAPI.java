package com.example.back_end.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class baseAPI {
    @GetMapping
    public String getRoot() {
        return "API Service is up and running";
    }
}
