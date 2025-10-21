package com.sc.bankapp.controller;

import com.sc.bankapp.model.Account;
import com.sc.bankapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id){
       Account account =  accountService.getAccountsById(id);

       return ResponseEntity.ok(account);
    }

    @PostMapping
    public  ResponseEntity<Account> createAccount(
            @RequestParam("customerId") Long customerId,
            @RequestBody Account accountRequest
    ){
        Account createdAccount = accountService.createAccount(customerId, accountRequest);
        return  ResponseEntity.ok(createdAccount);

    }

    @GetMapping("/by-customer/{customerId}")
    public Page<Account> getAccountsByCustomer(@PathVariable Long customerId, Pageable pageable){
        return  accountService.getAccountsByCustomer(customerId, pageable);
    }

//    @GetMapping("/by-customer/{customerId}")
//    public List<Account> getAccountsByCustomer(@PathVariable Long customerId){
//        return  accountService.getAccountsByCustomer(customerId);
//    }
}
