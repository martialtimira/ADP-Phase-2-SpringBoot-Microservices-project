package com.example.back_end.api;

import java.util.Optional;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.back_end.domain.Customer;
import com.example.back_end.repository.CustomersRepository;

@RestController
@RequestMapping("/customers")
public class CustomerAPI {
    @Autowired
    CustomersRepository customersRepo;

    @GetMapping
    public ResponseEntity<?> getCustomers() {
        return ResponseEntity.ok(customersRepo.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable long id) {
        Optional<Customer> customer = customersRepo.findById(id);
        if (customer.isPresent()) {
            return ResponseEntity.ok(customer.get());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer) {
        if (newCustomer.getId() != 0 || newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        newCustomer = customersRepo.save(newCustomer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newCustomer.getId()).toUri();

        return ResponseEntity.created(location).body(newCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customerToUpdate, @PathVariable long id) {
        if (customerToUpdate.getId() != id || customerToUpdate.getName() == null
                || customerToUpdate.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        customerToUpdate = customersRepo.save(customerToUpdate);
        return ResponseEntity.ok().body(customerToUpdate);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id) {
        Optional<Customer> customer = customersRepo.findById(id);
        if (customer.isPresent()) {
            customersRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).body("Customer with ID " + id + " not found.");
        }
    }

}
