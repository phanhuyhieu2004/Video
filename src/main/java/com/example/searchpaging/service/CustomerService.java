package com.example.searchpaging.service;

import com.example.searchpaging.CustomerRequest;
import com.example.searchpaging.CustomerSpec;
import com.example.searchpaging.PaginateRequest;
import com.example.searchpaging.model.Customer;
import com.example.searchpaging.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Page<Customer> findAll(PaginateRequest paginateRequest, CustomerRequest customerRequest) {
        return customerRepository.findAll(
                new CustomerSpec(customerRequest),
                PageRequest.of(paginateRequest.getPage(), paginateRequest.getSize())
        );
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }


    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }


    public void remove(Long id) {
        customerRepository.deleteById(id);
    }
}
