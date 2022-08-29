package com.mindhub.homebanking.services.implementServices;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service //le indico a la clase que su funcion va a ser la de ser un servicio
//Va a implementar la logica de los metodos de la interface de AccountServices
//Va a tener listo cada dato de antemano en vez de ejecutarlo en el mismo momento
public class AccountServiceImpl implements AccountServices {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override //sobreescribe al metodo de la interface
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @Override
    public AccountDTO getAccountById(Long id) {
      return  accountRepository.findById(id).map(AccountDTO::new).orElse(null); //busca el la cuenta del id que va a recibir por parametro

    }

    @Override
    public List<AccountDTO> getAccounts(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return client.getAccounts().stream().map(AccountDTO::new).collect(toList());
    }

    @Override
    public ResponseEntity<Object> postAccount(Authentication authentication) {
        Client clientConnected = clientRepository.findByEmail(authentication.getName());//encontramos a la persona autenticada por su email, tomamos sus datos almacenados en la BD a traves del repo
        //Obtenemos el cliente logueado (autenticado)
        if (clientConnected.getAccounts().size() > 2) { //Si el cliente conectado tiene 3 cuentas, le tira un error de prohibido
            return new ResponseEntity<>("Ya posees 3 cuentas", HttpStatus.FORBIDDEN); //NEW response entity te lleva al metodo constructor de la clase entity de java. Aca esta creando el objeto instanciado de entity con datos que se encuentran en su constructor (si ingreso voy a ver los http)
        }

        String numero = ((Integer)getRandomAccount(10000001, 100000000)).toString(); //lo pasamos a Integer que posee el metodo toString para poder pasarlo a String

        Account account = new Account("VIN" + numero, new Date(), 0.0, clientConnected); //con el boton de create del html me crea una cuenta automaticamente con los parametros pasados del objeto, y ademas lo asocia al cliente que esa conectado y autenticado en ese momento

        accountRepository.save(account); //guardamos la cuenta creada

        return new ResponseEntity<>("Cuenta creada" ,HttpStatus.CREATED); //una vez guardada nos responde que esta creada con el http CREATED
    }

    public int getRandomAccount(int min, int max) { //Creamos este metodo para pasar el numero random
        return (int) ((Math.random() * (max - min)) + min);
    }
}
