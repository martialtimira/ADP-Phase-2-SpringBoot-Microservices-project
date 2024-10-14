package com.example.back_end.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerAPI {
    @GetMapping()
    public String getCustomers() {
        return "TEST";
    }
}
