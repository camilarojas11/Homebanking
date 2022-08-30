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

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class CardServiceImpl implements CardServices {
    @Autowired
    CardRepository cardRepository;
    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<CardDTO> getAll() {
        return cardRepository.findAll().stream().map(CardDTO::new).collect(toList());
    }

    @Override
    public CardDTO getAccount(Long id) {
      return  cardRepository.findById(id).map(CardDTO::new).orElse(null);
    }

    @Override
    public ResponseEntity<Object> postCard(Authentication authentication, CardType type, CardColor color) {
        Client clientConnected = clientRepository.findByEmail(authentication.getName());

        if(cardRepository.findByTypeAndClient(type, clientConnected).size() > 2){
            return new ResponseEntity<>("No puedes agregar más tarjetas de tipo "+type, HttpStatus.FORBIDDEN);
        }

        String cardHolder = clientConnected.getFirstName() + " " + clientConnected.getLastName();

        cardRepository.save(new Card(cardHolder, type, color, CardUtils.getCardNumber(1001, 10000), CardUtils.getCVVNumber(101, 1000),
                LocalDate.now(), LocalDate.now().plusYears(5), clientConnected));

        return new ResponseEntity<>("Tarjeta creada" ,HttpStatus.CREATED);
    }
    }

