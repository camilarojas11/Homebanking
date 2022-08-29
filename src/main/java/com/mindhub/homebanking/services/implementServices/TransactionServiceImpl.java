package com.mindhub.homebanking.services.implementServices;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.services.TransactionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TransactionServiceImpl implements TransactionServices {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ClientRepository clientRepository;


    @Override
    public ResponseEntity<Object> newTransaction(double amount, String description, String fromAccountNumber, String toAccountNumber, Authentication authentication) {
        Client clientConnected = clientRepository.findByEmail(authentication.getName()); //Utiliza el metodo findbyemail para encontrar al usuario autenticado y obtenerlo.
        Account accountOrigin = accountRepository.findByNumber(fromAccountNumber); //encuentra y crea como obj a la cuenta que el usuario esta ingresando
        Account accountDestiny = accountRepository.findByNumber(toAccountNumber);

        if (amount==0 || description.isEmpty() || fromAccountNumber.isEmpty() || toAccountNumber.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (fromAccountNumber.equals(toAccountNumber)) {
            return new ResponseEntity<>("El origen y destino son la misma cuenta", HttpStatus.FORBIDDEN);
        }
        if (accountOrigin==  null) {
            return new ResponseEntity<>("La cuenta de origen no corresponde con ninguna cuenta registrada", HttpStatus.FORBIDDEN);
        }
        if (!accountRepository.findByClient(clientConnected).contains(accountOrigin)) { // Vamos al repo de cuentas, las filtramos por cliente, buscamos al cliente que este conectado en ese momento y le preguntamos si contiene esa cuenta de origen
            return new ResponseEntity<>("La cuenta no pertenece al cliente autenticado", HttpStatus.FORBIDDEN);
        }
        if (accountDestiny==  null) {
            return new ResponseEntity<>("La cuenta de destino no corresponde con ninguna cuenta registrada", HttpStatus.FORBIDDEN);
        }
        if (accountOrigin.getBalance()<amount){
            return new ResponseEntity<>("La cuenta de origen no tiene el saldo disponible para esta transacción", HttpStatus.FORBIDDEN);
        }

        Transaction transactionOrigin=new Transaction(TransactionType.DEBIT,-amount,toAccountNumber+" "+description,new Date(), accountOrigin);
        Transaction transactionDestiny=new Transaction(TransactionType.CREDIT,amount,fromAccountNumber+" "+description,new Date(), accountDestiny);
        transactionRepository.save(transactionOrigin);
        transactionRepository.save(transactionDestiny);

        double balanceNuevo=accountOrigin.getBalance() - amount;
        accountOrigin.setBalance(balanceNuevo);
        accountRepository.save(accountOrigin);
        balanceNuevo=accountDestiny.getBalance() + amount;
        accountDestiny.setBalance(balanceNuevo);
        accountRepository.save(accountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
