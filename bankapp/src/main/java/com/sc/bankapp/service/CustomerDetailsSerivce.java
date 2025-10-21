package com.sc.bankapp.service;

import com.sc.bankapp.exception.ResourceNotFoundException;
import com.sc.bankapp.model.Customer;
import com.sc.bankapp.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class  CustomerDetailsSerivce  implements UserDetailsService {

    @Autowired
    private CustomerRepo customerRepo;

//    @Autowired
//    public  CustomerDetailsSerivce(CustomerRepo customerRepo){
//        this.customerRepo = customerRepo;
//    }
//

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Customer customer = customerRepo.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found by email of: " + email)
        );

        String role = customer.getRole()  != null ? customer.getRole() : "USER";

        return User.builder()
                .username(customer.getEmail())
                .password(customer.getPassword())
                .roles(role)
                .disabled(isNotActive(customer))
                .build();

    }

    private  boolean  isNotActive(Customer customer){
        String active = customer.getActive();
        if(active == null) return  false;
        return  !(active.equalsIgnoreCase("true") || active.equalsIgnoreCase("y") || active.equals("1"));
    }
}
