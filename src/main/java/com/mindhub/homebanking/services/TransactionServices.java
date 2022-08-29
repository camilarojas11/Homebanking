package com.mindhub.homebanking.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

public interface TransactionServices {
     ResponseEntity<Object> newTransaction(@RequestParam double amount, @RequestParam String description,
                                                 @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, Authentication authentication);


}
