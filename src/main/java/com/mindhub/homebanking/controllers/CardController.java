package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.services.CardServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    public CardServices cardServices;

    @GetMapping("/cards")
    public List<CardDTO> getAll(){
        return cardServices.getAll();
    }

    @GetMapping("/cards/{id}")
    public CardDTO getAccount(@PathVariable Long id){
        return cardServices.getAccount(id);
    }
   //Poder crear tarjetas con frontend con las condiciones de las US (no mas de 3 de cada tipo, credito y debito)
    @PostMapping("/clients/current/cards")
    public ResponseEntity<Object> postCard(Authentication authentication, @RequestParam CardType type, @RequestParam CardColor color){ //Traigo los requestparam porque voy a tener un formulario con html para llenar estos datos
    return cardServices.postCard(authentication, type, color);
    }

}

