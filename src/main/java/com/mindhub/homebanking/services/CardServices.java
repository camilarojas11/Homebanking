package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CardServices {

    List<CardDTO> getAll();
    CardDTO getAccount(Long id);
    ResponseEntity<Object> postCard(Authentication authentication, @RequestParam CardType type, @RequestParam CardColor color);
}
