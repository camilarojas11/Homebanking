package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.services.AccountServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountServices accountServices; //en vez de traer el repo de accounts, como esto va a estar manejado por el services, ahora empezamos a traer el services de accounts que tb es una interfaz
    //private AccountRepository accountRepository; -->  Ya no lo necesito acá, lo usa el Service ahora y se encarga el de pedirle cosas al repo
  //solo puedo inyectar repositorios (interfaces) con el autowired. Hago sto para poder guardar las cuentas

    @GetMapping ("/accounts")
    public List<AccountDTO> getAccounts() {
       return accountServices.getAllAccounts();

    }

    @GetMapping("/accounts/{id}")
    public AccountDTO getAccountById (@PathVariable Long id){

        return accountServices.getAccountById(id);
    }

            //Poder crear cuentas con frontend y las condiciones de las US (no mas de tres cuentas).

    @PostMapping("/clients/current/accounts") //al autenticarme manda un token al frontend y me genera una interfaz que geenra algo similar al token.
            //creamos una entidad de respuesta del servidor al cliente:
            //OBJECT es la clase principal dentro de java y engloba todos los tipos de datos ,por tanto puede devolver cualquier tipo de dato.
    private ResponseEntity<Object> postAccount(Authentication authentication) { // lo que pase en la ruta que hice antes va a devolver una respuesta respecto de los datos de usuario autenticado que estan entre parentesis
      return accountServices.postAccount(authentication);
    }



    //Traemos la lista de cuentas
    @GetMapping("/clients/current/accounts")
    public List<AccountDTO> getAccounts(Authentication authentication){
        return accountServices.getAccounts(authentication);
    }


}

