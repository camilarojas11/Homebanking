package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.ClientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //crea el REST CONTROLLER
@RequestMapping("/api") //voy definiendo las rutas que vamos a usar para hacer la peticion para este controlador. Para llamar a un metodo de este controlador, la peticion arranca con /api
public class ClientController {
    @Autowired //Inyecta y me deja disponible el repositorio para poder usarlo
    //private ClientRepository clientRepository;
    ClientServices clientServices;

    @Autowired

    private PasswordEncoder passwordEncoder;



    @GetMapping("/clients")//Por defecto el metodo que uso es GETS, si quisiera otro metodo deberia especificarlo con el value="/clients", method=RequestMethod.POST o puede ser .GET tb, por mas q sea asi por defecto).
                                    //Tambien puedo usar getmapping o postmapping
    public List<ClientDTO>getAllClients(){ //Metodo heredado de JPA Repository: Los retorna como JSON para que el frontend se encargue de transformarlos y adaptarlos como necesite con la tecnologia que desee, no necesariamente con java. JSON es el dato crudo y sin ataduras a tecnologias en particular
        return clientServices.getAllClients();
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClientById(@PathVariable Long id){
        return clientServices.getClientById(id);
    }

    @GetMapping("/clients/nombre/{nombre}")
    public List<ClientDTO> getClientByFirstName (@PathVariable String nombre){//parametro que utilizo (nombre)de la url
        return clientServices.getClientByFirstName(nombre);
    }

    @GetMapping("/clients/email/{email}")
    public Client getClientByEmail (@PathVariable String email) {//parametro que utilizo (nombre)de la url
        return clientServices.getClientByEmail(email);
    }
    @GetMapping("/clients/nombreyapellido")
    public List<ClientDTO> getClientByNombreyApellido(){ //parametro que utilizo (nombre)de la url
        return clientServices.getClientByNombreyApellido();
    }

    @GetMapping("/clients/current") //Para que ya no me muestre el "1" sino el actual, la info del cliente con el que me loguee. Por mas que aparezca el nombre del cliente que logueo, la info va a ser del otro.
    public ClientDTO getClient(Authentication authentication){
       return clientServices.getClient(authentication);
    }
   //Poder crear clientes (sign up) con frontend.
    @PostMapping("/clients")//(path =  //, method = RequestMethod.POST)

    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName, //recibo estos parametros por el formulario de frontend

            @RequestParam String email, @RequestParam String password) {


        return clientServices.register(firstName, lastName, email, password);
    }

    }

