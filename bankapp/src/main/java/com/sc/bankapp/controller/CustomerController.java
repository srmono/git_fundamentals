package com.sc.bankapp.controller;

import com.sc.bankapp.model.Account;
import com.sc.bankapp.model.Customer;
import com.sc.bankapp.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "http://localhost:3000")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

//    @GetMapping
//    public  String getMessage(){
//        return  "Hello user I am an api";
//    }

    @PostMapping("/register")
    public ResponseEntity<Customer> register(@RequestBody Customer newCustomer) {
        Customer saved = customerService.createCustomer(newCustomer);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public List<Customer> getCustomer(){
        List<Customer> customerList = customerService.getAllCustomers();

        return customerList;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer>  getCustomerById(@PathVariable Long id){
        Customer customer =  customerService.getCustomerById(id);
        return  ResponseEntity.ok(customer);
    }
//
    //Create customer
    @PostMapping
    public  Customer createCustomer(@RequestBody Customer newCustomer){
        return  customerService.createCustomer(newCustomer);
    }

        // Update customer (replace)
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(
            @PathVariable Long id,
            @RequestBody Customer updatedCustomer
    ) {
        Customer customer = customerService.updateCustomer(id, updatedCustomer);
        return ResponseEntity.ok(customer);
    }

    @GetMapping("/byemail")
    public Optional<Customer>  getByEmailQuery( String email){
        return  customerService.findByEmailUsingQuery(email);
    }

    @PostMapping("/with-account")
    public ResponseEntity<Customer> createCustomerWithAccount(
            @RequestBody Customer customer,
            @RequestBody Account account
    ) {
        Customer savedCustomer = customerService.createCustomerWithAccount(customer, account);
        return ResponseEntity.ok(savedCustomer);
    }


//
//    // Update customer (replace)
//    @PutMapping("/{id}")
//    public ResponseEntity<Customer> updateCustomer(
//            @PathVariable Long id,
//            @RequestBody Customer updatedCustomer
//    ) {
//        Customer customer = customerService.updateCustomer(id, updatedCustomer);
//        return ResponseEntity.ok(customer);
//    }
//
//    // Delete customer
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
//        customerService.deleteCustomer(id);
//        return ResponseEntity.noContent().build();
//    }


}
