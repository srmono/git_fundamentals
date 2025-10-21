package com.sc.bankapp.service;

import com.sc.bankapp.exception.ResourceNotFoundException;
import com.sc.bankapp.model.Account;
import com.sc.bankapp.model.Customer;
import com.sc.bankapp.repository.AccountRepo;
import com.sc.bankapp.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerRepo customerRepo;

    //get account by customer
    public Page<Account> getAccountsByCustomer(Long customerId, Pageable pageable){
        if(!customerRepo.existsById(customerId) ){
            throw  new ResourceNotFoundException("Customer not found with ID: "+ customerId);
        }

        return  accountRepo.findByCustomerId(customerId, pageable);
    }

    public  Account getAccountsById(Long id){
        return  accountRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Account not found with ID: " + id)
        );
    }

    public Account createAccount(Long customerId, Account account){
        Customer customer = customerRepo.findById(customerId)
                                .orElseThrow(
                                        () -> new ResourceNotFoundException("Customer not found with ID: "+ customerId)
                                );

        account.setCustomer(customer);

        return  accountRepo.save(account);
    }

    //update account
    //delete account
}
