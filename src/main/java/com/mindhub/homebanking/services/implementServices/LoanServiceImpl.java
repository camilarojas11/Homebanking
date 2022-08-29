package com.mindhub.homebanking.services.implementServices;

import com.mindhub.homebanking.dtos.LoanAplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.LoanRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.LoanServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class LoanServiceImpl implements LoanServices {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Override
    public ResponseEntity<Object> newLoan(LoanAplicationDTO loanAplicationDTO, Authentication authentication) {
        Client clientConnected = clientRepository.findByEmail(authentication.getName()); //datos del cliente conectado; getName indica que el que esta conectado le pasa los parametros de ese cliente (va a tomar los datos de ese cliente que se esta conectando)
        Account accountDestiny = accountRepository.findByNumber(loanAplicationDTO.getToAccountNumber()); //trae el objeto (cuenta) que corresponde al numero de cuenta que coloca el usuario y lo trae con el get //los datos de la cuenta estan en loanAplicationDTO, alli la persona pone los datos de la cuenta donde va a dirigirse el prestamo
        Loan typeLoan = loanRepository.findById(loanAplicationDTO.getLoanId()).orElse(null); //Tipo de prestamo (elegir la cant de cuotas etc). Necesito saber lo que la persona coloco en el loanaplicationDTO (frontend). LoanID es el tipo de prestamo. Aca uso el TypeId porque me trae el id de cada prestamo, yo en el front al elegir un prestamo me lo busca pero es OPCIONAl porque puede como puede NO encontrar el ID si lo que wligw la persona no existe, ej tipo de prestamo "empresarial" (no existe). Le pusimos la condicion de q si no lo encuentra tire null directamente

        if (loanAplicationDTO.getAmount() == 0 || loanAplicationDTO.getLoanId()==0 || loanAplicationDTO.getPayments() == 0) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (typeLoan == null) { //va trayendo los datos del objeto en loanAplicationDTO con su respectivo getter (getLoanId)
            return new ResponseEntity<>("El tipo de prestamo no existe", HttpStatus.FORBIDDEN);
        }
        if (typeLoan.getMaxAmount() < loanAplicationDTO.getAmount()) { //toma el tipo de prestamo de la clase LOANS y se fija que el maxamount sea menor que el que puso la persona en las opciones del formulario
            return new ResponseEntity<>("Se exedió el máximo monto para este tipo de prestamo", HttpStatus.FORBIDDEN);
        }
        if (!typeLoan.getPayments().contains(loanAplicationDTO.getPayments())) { //si el array contiene el dato que pone el cliente al pedir el prestamo
            return new ResponseEntity<>("La cuotas no son válidas", HttpStatus.FORBIDDEN);
        }
        if (accountDestiny == null) { //no existe
            return new ResponseEntity<>("La cuenta de destino no corresponde con ninguna cuenta registrada", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.findByClient(clientConnected).contains(accountDestiny)) { //valida que la cuenta destino
            //sea del cliente conectado en ese momento (si no esta tira errror)
            return new ResponseEntity<>("La cuenta no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }

        //El cliente que solicita el prestamo es ClienLoan, representa al prestamo ya solicitado:
        ClientLoan clientLoanCreated = new ClientLoan(loanAplicationDTO.getAmount() + (loanAplicationDTO.getAmount() * 0.2), loanAplicationDTO.getPayments(), clientConnected, typeLoan);
        //Transferimos el prestamo con Transaction: (SIN EL 20%, porque este es el prestamo realmente recibido no lo que yo tengo que pagar despues con intereses)
        Transaction transactionDestiny = new Transaction(TransactionType.CREDIT, loanAplicationDTO.getAmount(), loanAplicationDTO.getLoanId() + "Loan Approved", new Date(), accountDestiny); //datos de la transaccion que acabamos de hacer, tomando los datos de clientloanapplication (lo que el usuario fue poniendo en el formulario)
        transactionRepository.save(transactionDestiny);


        Double balanceNuevo = accountDestiny.getBalance() + loanAplicationDTO.getAmount(); //actualizando el balance tomando el balance de la cuenta destino mas el que el cliente esta solicitando como prestamo
        accountDestiny.setBalance(balanceNuevo);
        accountRepository.save(accountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public List<LoanDTO> getAll() {
        return loanRepository.findAll().stream().map(LoanDTO::new).collect(toList());
    }
}
