package com.mindhub.homebanking.services.implementServices;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClientServiceImpl implements ClientServices {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public List<ClientDTO> getAllClients() {
        return clientRepository.findAll().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());//me devuelve una lista de clientes
    }

    @Override
    public ClientDTO getClientById(Long id) {
        return clientRepository.findById(id).map(ClientDTO::new).orElse(null);
    }

    @Override
    public List<ClientDTO> getClientByFirstName(String nombre) {
        return clientRepository.findByFirstName(nombre).stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());//devuelve una lista con los nombres (porque hay mas de uno con el mismo)
    }

    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public List<ClientDTO> getClientByNombreyApellido() {
        return clientRepository.findByFirstNameAndLastName("Ana", "Marquez").stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @Override
    public ClientDTO getClient(Authentication authentication) {
        Client client = this.clientRepository.findByEmail(authentication.getName());
        return new ClientDTO(client);
    }

    @Override
    public ResponseEntity<Object> register(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {

            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);

        }



        if (clientRepository.findByEmail(email) !=  null) {

            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);

        }



        Client client1 = new Client (firstName, lastName, email, passwordEncoder.encode(password));
        clientRepository.save(client1);

        String numero = ((Integer)getRandomAccount(10000001, 100000000)).toString(); //lo pasamos a Integer que posee el metodo toString para poder pasarlo a String

        Account account = new Account("VIN" + numero, new Date(), 0.0, client1);
        accountRepository.save(account);

        return new ResponseEntity<>(HttpStatus.CREATED); //si pasó los anteriores requisitos, lo crea, lo guarda y envia un mensaje 201 creado

    }
    public int getRandomAccount(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
