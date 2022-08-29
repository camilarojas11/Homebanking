package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.ClientLoan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource //agrego la anotación y me crea esa API, una ruta, dirección o endpoint
public interface ClientLoanRepository extends JpaRepository<ClientLoan, Long> { //este servicio me permite acceder a la info desde la base de datos
                                                            //El repo trabaja con el tipo de dato Client (es la entidad, convertida así para que internamente se genere una tabla, que se encuentra en models)
}
