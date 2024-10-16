package com.example.back_end.api;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.back_end.domain.Customer;
import com.example.back_end.repository.CustomersRepository;
import com.example.back_end.security.TokenService;
import java.net.URI;

@RestController
public class AuthAPI {

    @Autowired
    CustomersRepository customersRepo;

    @Autowired
    TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody Customer requestCustomer) {
        String username = requestCustomer.getName();
        String password = requestCustomer.getPassword();

        for (Customer customer : customersRepo.findAll()) {
            if (customer.getName().equals(username) && customer.getPassword().equals(password)) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username, password, null);

                Map<String, String> tokenResponse = tokenService.generateToken(authentication);

                return ResponseEntity.ok(tokenResponse);
            }
        }

        return ResponseEntity.status(401).body("Unauthorized: Invalid credentials");
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@RequestBody Customer newCustomer) {

        for (Customer customer : customersRepo.findAll()) {
            if (customer.getName().equals(newCustomer.getName())) {
                return ResponseEntity.status(409).body("Customer with this name already exists");
            }
        }
        if (newCustomer.getId() != 0 || newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().body("Invalid customer data");
        }

        newCustomer = customersRepo.save(newCustomer);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCustomer.getId())
                .toUri();

        return ResponseEntity.created(location).body(newCustomer);
    }
}
