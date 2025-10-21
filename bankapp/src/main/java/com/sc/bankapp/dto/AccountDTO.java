package com.sc.bankapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
    private Long id;
    private Integer accountType;
    private String accountNumber;
    private String accountBranch;
    private BigDecimal accountBalance;
    private Long customerId;
}

