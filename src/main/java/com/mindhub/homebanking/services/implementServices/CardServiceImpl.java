package com.mindhub.homebanking.services.implementServices;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.services.CardServices;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImpl implements CardServices {
    @Autowired
    CardRepository repo;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<CardDTO> getAll() {
        return repo.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @Override
    public CardDTO getAccount(Long id) {
      return  repo.findById(id).map(CardDTO::new).orElse(null);
    }

    @Override
    public ResponseEntity<Object> postCard(Authentication authentication, CardType type, CardColor color) {
        Client clientConneted = clientRepository.findByEmail(authentication.getName());

        if(repo.findByTypeAndClient(type, clientConneted).size() > 2){
            return new ResponseEntity<>("No puedes agregar más tarjetas de tipo "+type, HttpStatus.FORBIDDEN);
        }

        String cardNumber = CardUtils.getCardNumber(1001, 10000);


        int cvv = CardUtils.getCVVNumber(101, 1000);

        Card card1 = new Card (color, type, cardNumber, cvv, clientConneted);

        repo.save(card1); //guardamos la cuenta creada

        return new ResponseEntity<>("Tarjeta creada" ,HttpStatus.CREATED);
    }
    }

