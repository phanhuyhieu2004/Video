package com.example.searchpaging.controller;

import com.example.searchpaging.CustomerRequest;
import com.example.searchpaging.ErrorMsg;
import com.example.searchpaging.PaginateRequest;
import com.example.searchpaging.model.Customer;
import com.example.searchpaging.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMsg> handleException(Exception ex) {
        return new ResponseEntity<>(new ErrorMsg(ex.getMessage(), "500"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping
    public ResponseEntity<Iterable<Customer>> getAll(
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "1") int size,
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName
    ) throws Exception {
//        throw new Exception("Có lỗi");
        Page<Customer> pages = customerService.findAll(
                new PaginateRequest(page, size),
                new CustomerRequest(firstName, lastName)
        );
           return new ResponseEntity<>(pages.getContent(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer customerCreated = customerService.save(customer);
        return new ResponseEntity<>(customerCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable long id, @RequestBody Customer customer){
        Optional<Customer> findCustomer = customerService.findById(id);
        if(!findCustomer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        findCustomer.get().setFirstName(customer.getFirstName());
        findCustomer.get().setLastName(customer.getLastName());

       Customer customerUpdated = customerService.save(findCustomer.get());

        return new ResponseEntity<>(customerUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Customer> delete(@PathVariable long id) {
        Optional<Customer> findCustomer = customerService.findById(id);
        if(!findCustomer.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        customerService.remove(id);
        return new ResponseEntity<>(findCustomer.get(), HttpStatus.OK);
    }
}
