package com.example.back_end.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.back_end.domain.Customer;

@RestController
@RequestMapping("/customers")
public class CustomerAPI {
    ArrayList<Customer> customerList = new ArrayList<Customer>();

    public CustomerAPI() {
        Customer c1 = new Customer(1, "Steve", "pass", "steve@abc.com");
        Customer c2 = new Customer(2, "Bob", "pass", "bob@abc.com");
        Customer c3 = new Customer(3, "Cindy", "pass", "cindy@abc.com");
        customerList.add(c1);
        customerList.add(c2);
        customerList.add(c3);
    }

    @GetMapping()
    public ResponseEntity<?> getCustomers() {
        return ResponseEntity.ok(customerList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable long id) {
        for (Customer customer : customerList) {
            if (customer.getId() == id) {
                return ResponseEntity.ok(customer);
            }
        }

        return ResponseEntity.notFound().build();
    }
}
