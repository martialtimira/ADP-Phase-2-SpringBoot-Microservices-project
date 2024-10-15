package com.example.back_end.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.back_end.domain.Customer;

public interface CustomersRepository extends CrudRepository<Customer, Long> {

}
