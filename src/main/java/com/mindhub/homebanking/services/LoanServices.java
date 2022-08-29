package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface LoanServices {
    ResponseEntity<Object> newLoan(@RequestBody LoanAplicationDTO loanAplicationDTO, //recibe el objeto que manda front
                                   Authentication authentication);
    List<LoanDTO> getAll();



}
