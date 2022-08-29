package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TransactionRestController {

    @Autowired
    TransactionServices transactionServices;

    @Transactional //Garantiza la atomicidad, es decir que funcione aunque se corte la luz
    @PostMapping("/transactions")
    public ResponseEntity<Object> newTransaction(
            @RequestParam double amount, @RequestParam String description,
            @RequestParam String fromAccountNumber, @RequestParam String toAccountNumber, Authentication authentication) {
        return transactionServices.newTransaction(amount, description, fromAccountNumber, toAccountNumber, authentication);
    }

}
