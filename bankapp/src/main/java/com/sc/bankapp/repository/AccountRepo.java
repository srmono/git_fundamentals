package com.sc.bankapp.repository;

import com.sc.bankapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
   // Page<Account> findByCustomerId(Long customerId, Pageable pageable);

//    List<Account> findByCustomerId(Long customerId);

    Page<Account> findByCustomerId(Long customerId, Pageable pageable);

    Optional<Account> findByAccountNumber(String accountNumber);

    // simple example @Query in JPQL — keep it minimal
    @Query("SELECT a FROM Account a WHERE a.accountNumber = :accNo")
    Optional<Account> findByAccountNumberUsingQuery(@Param("accNo") String accountNumber);
}
