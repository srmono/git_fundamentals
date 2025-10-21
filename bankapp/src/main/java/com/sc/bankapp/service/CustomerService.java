package com.sc.bankapp.service;

import com.sc.bankapp.dto.AccountDTO;
import com.sc.bankapp.dto.CustomerDTO;
import com.sc.bankapp.exception.ResourceNotFoundException;
import com.sc.bankapp.model.Account;
import com.sc.bankapp.model.Customer;
import com.sc.bankapp.repository.AccountRepo;
import com.sc.bankapp.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Customer> getAllCustomers(){
        return  customerRepo.findAll();

    }

    @Transactional
    public Customer createCustomerWithAccount(Customer customer, Account account){

       Customer savedCustomer  =  customerRepo.save(customer);

       account.setCustomer(savedCustomer);

        accountRepo.save(account);

        return  savedCustomer;
    }

    public Customer getCustomerById(Long id){
        return  customerRepo.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Customer not found with ID: "+ id)
                );
    }

    @Transactional
    public  Customer createCustomer(Customer newCustomer){

        if(newCustomer.getPassword() != null){
            newCustomer.setPassword(
                    passwordEncoder.encode(
                            newCustomer.getPassword()
                    )
            );
        }

        if(newCustomer.getAccounts() !=null){
            newCustomer
                    .getAccounts()
                    .forEach(
                            acc -> acc.setCustomer(newCustomer)
                    );
        }
        return  customerRepo.save(newCustomer);
    }



    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = customerRepo.findById(id)
                                .orElseThrow(
                                        () -> new ResourceNotFoundException("Customer not found with ID: "+ id)
                                );

        existing.setFirstName(updatedCustomer.getFirstName());
        existing.setLastName(updatedCustomer.getLastName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setActive(updatedCustomer.getActive());
        existing.setPassword(updatedCustomer.getPassword());
        existing.setRole(updatedCustomer.getRole());

        if(updatedCustomer.getAccounts() !=null){
            existing.getAccounts().clear();

            updatedCustomer.getAccounts().forEach( acc -> {
                acc.setCustomer(existing);
                existing.getAccounts().add(acc);
            });
        }

        return customerRepo.save(existing);
    }

    public void deleteCustomer(Long id) {
        boolean exists = customerRepo.existsById(id);

        if(!exists){
          throw  new ResourceNotFoundException("Customer not found with ID: "+ id);
        }
        customerRepo.deleteById(id);
    }

    //Derived query usage
    public Optional<Customer> findByEmail(String email){
        return customerRepo.findByEmail(email);
    }

    //@query usage
    public Optional<Customer> findByEmailUsingQuery(String email){
        return customerRepo.findByEmailUsingQuery(email);
    }




//    public List<Customer> getAllCustomers(){
//        return  customerRepo.findAll();
//    }
//
//    public Customer getCustomerById(Long id){
//        return  customerRepo.findById(id)
//                .orElseThrow(
//                        () -> new ResourceNotFoundException("Customer not found with ID: "+ id)
//                );
//    }
//
//    public  Customer createCustomer(Customer newCustomer){
//        return  customerRepo.save(newCustomer);
//    }
//
//
//    public Customer updateCustomer(Long id, Customer updatedCustomer) {
//        Customer existing = customerRepo.findById(id)
//                                .orElseThrow(
//                                        () -> new ResourceNotFoundException("Customer not found with ID: "+ id)
//                                );
//
//        existing.setFirstName(updatedCustomer.getFirstName());
//        existing.setLastName(updatedCustomer.getLastName());
//        existing.setEmail(updatedCustomer.getEmail());
//        existing.setPhone(updatedCustomer.getPhone());
//        existing.setActive(updatedCustomer.getActive());
//        existing.setPassword(updatedCustomer.getPassword());
//        existing.setRole(updatedCustomer.getRole());
//
//        return customerRepo.save(existing);
//    }
//
//    public void deleteCustomer(Long id) {
//        boolean exists = customerRepo.existsById(id);
//
//        if(!exists){
//          throw  new ResourceNotFoundException("Customer not found with ID: "+ id);
//        }
//        customerRepo.deleteById(id);
//    }
//
//    //Derived query usage
//    public Optional<Customer> findByEmail(String email){
//        return customerRepo.finByEmall(email);
//    }
//
//    //@query usage
//    public Optional<Customer> findByEmailUsingQuery(String email){
//        return customerRepo.findByEmailUsingQuery(email);
//    }




//    List<Customer> customers = new ArrayList<>();
//
//    public List<Customer> getAllCustomers(){
//
//        customers.add(new Customer(1L, "Ravi", "Kumar", "ravi@example.com",
//                "9000000001", "true", "pass123", "USER"));
//        customers.add(new Customer(2L, "Meera", "Shah", "meera@example.com",
//                "9000000002", "true", "meera321", "ADMIN"));
//        customers.add(new Customer(3L, "Anil", "Rao", "anil@example.com",
//                "9000000003", "false", "anilpwd", "USER"));
//
//        return  customers;
//
//    }
//
//    public Customer getCustomerById(Long id){
//        return  getAllCustomers()
//                    .stream()
//                    .filter(
//                            cust -> cust.getId() == id
//                    )
//                    .findFirst()
//                    .orElseThrow(
//                            () -> new ResourceNotFoundException("Customer not found with ID: "+ id)
//                    );
//
//    }
//
//    public  Customer createCustomer(Customer newCustomer){
//        newCustomer.setId(100L); // dummy id
//
//        customers.add(newCustomer);
//
//        return  newCustomer;
//    }
//
//    public Customer updateCustomer(Long id, Customer updatedCustomer) {
//        // Find existing customer
//        Customer existingCustomer = getAllCustomers()
//                .stream()
//                .filter(c -> c.getId() == id)
//                .findFirst()
//                .orElseThrow(() ->
//                        new ResourceNotFoundException("Customer not found with ID: " + id)
//                );
//
//        // Update fields
//        existingCustomer.setFirstName(updatedCustomer.getFirstName());
//        existingCustomer.setLastName(updatedCustomer.getLastName());
//        existingCustomer.setEmail(updatedCustomer.getEmail());
//        existingCustomer.setPhone(updatedCustomer.getPhone());
//        existingCustomer.setActive(updatedCustomer.getActive());
//        existingCustomer.setPassword(updatedCustomer.getPassword());
//        existingCustomer.setRole(updatedCustomer.getRole());
//
//        return existingCustomer;
//    }
//
//    public void deleteCustomer(Long id) {
//        boolean removed = getAllCustomers()
//                .removeIf(c -> c.getId() == id);
//
//        if (!removed) {
//            throw new ResourceNotFoundException("Customer not found with ID: " + id);
//        }
//    }

}
