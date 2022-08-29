package com.mindhub.homebanking.services;

//Colocamos esta capa para no estresar tanto al servidor solicitando todo al controlador. En esta clase no heredamos nada.
//La interfaz indica que accion va a realizar el service, es un intermediario que le pasa los metodos a la clase y esta los sobreescribe. La interfaz los invoca, la clase los crea (la logica) y el controlador los llama

import com.mindhub.homebanking.dtos.AccountDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AccountServices {

    //Necesito que me devuelva una lista, colocamos el nombre del metodo que yo elija (en este caso le pongo getAllAccounts
    List<AccountDTO> getAllAccounts();
    AccountDTO getAccountById(Long id);

    List<AccountDTO> getAccounts(Authentication authentication);

    ResponseEntity<Object> postAccount(Authentication authentication);

}
