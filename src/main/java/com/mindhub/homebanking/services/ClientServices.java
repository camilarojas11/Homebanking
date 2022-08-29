package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ClientServices {
    List<ClientDTO> getAllClients();
    ClientDTO getClientById(Long id);
    List<ClientDTO> getClientByFirstName (String nombre);
    Client getClientByEmail (String email);
    List<ClientDTO> getClientByNombreyApellido();
    ClientDTO getClient(Authentication authentication);
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName, //recibo estos parametros por el formulario de frontend

            @RequestParam String email, @RequestParam String password);



}
