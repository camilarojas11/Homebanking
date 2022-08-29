package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
//Client Repository no es más que una interfaz de la cual Heredamos métodos con JPA Repository
//Tiene metodos de findall, etc. A su vez hereda de crudrepository, que tiene metodos mas basicos como el de save (homebancking al guardar los objetos)
@RepositoryRestResource //agrego la anotación y me crea la API, podré acceder a los datos con la direcciónlocalhost:8080/rest/accounts. Debo agregar en application.properties dos direcciones y luego crear los REST CONTROLLER (ClientController y AccountController) que me permitirán acceder a localhost:8080/api/clients y ver los datos del cliente + cuentas asociadas de forma mas amigable q con el /rest
public interface ClientRepository extends JpaRepository<Client, Long> { //Long es el tipo de dato del ID

    Client findByEmail (String email); //creo la query creando el metodo especifico para buscar por email.
    //Los query puedo hacerlos con dos campos usando palabras claves: AND (findByEmailAndName). Tambien puedo usar distinct, or, etc.
    List<Client> findByFirstName (String firstName); //por si hay mas de una persona con el mismo nombre puede devolver todos

    //server.port=8081 : SI NO LEVANTA BIEN EL PUERTO EN EL SERVIDOR, coloco esta ruta en application.properties
    List<Client> findByFirstNameAndLastName(String firstName, String LastName);



}
