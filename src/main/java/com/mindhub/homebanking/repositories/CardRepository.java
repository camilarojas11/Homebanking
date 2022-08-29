package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface CardRepository  extends JpaRepository<Card, Long> {

    List<Card> findByTypeAndClient(CardType type, Client client); //creamos esta busqueda con los parametros para en el cardcontroller poder hacer la condicion de que si encuentra 3 tipos de cuenta debito o credito, no le deje crear mas

}
