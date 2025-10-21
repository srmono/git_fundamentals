package com.sc.bankapp.repository;

import com.sc.bankapp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    @Query("select c from Customer c where c.email = :email")
    Optional<Customer> findByEmailUsingQuery(@Param("email") String email);


}
