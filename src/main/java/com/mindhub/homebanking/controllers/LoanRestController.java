package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.services.LoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LoanRestController {

    @Autowired
    LoanServices loanServices;



    @Transactional
    //Al sacar un prestamo tambien hacemos una transaccion
    @PostMapping("/loans")
    //GetById es opcional por tanto debo colocar el get() para que me traiga lo que yo necesito.
    public ResponseEntity<Object> newLoan(@RequestBody LoanAplicationDTO loanAplicationDTO, //recibe el objeto que manda front
                                          Authentication authentication) {

        return loanServices.newLoan(loanAplicationDTO, authentication);


    }

    @GetMapping("/loans") //traigo la lista de prestamos disponibles (sin el clientDTO el cliente no va a poder acceder)
    public List<LoanDTO> getAll() {
        return loanServices.getAll();

    }
}
