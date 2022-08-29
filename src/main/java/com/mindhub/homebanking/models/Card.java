package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private CardColor color;
    private CardType type;
    private String number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client; //Acá agrego el "cardHolder" que pedía la consiga. Así, agrego el client como un parametro más que traigo
    // de la entidad cliente y lo voy a crear directamente linkeando el client1 o client2 con la tarjeta, siendo un parametro más de este obj. Card.
    // del constructor cuando hago el new object.  Otra forma de hacerlo sería hacer el método add directamente en Client y luego en HomeBancking lo linkeo con los métodos client1.addCard(card1).

    public Card() {
    }


    public Card(CardColor color, CardType type, String number, int cvv, Client client) {
        this.color = color;
        this.type = type;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = calcularFechaVencimiento();
        this.fromDate = LocalDate.now();
        this.client = client;

    }

    private LocalDate calcularFechaVencimiento(){
      return  LocalDate.now().plusYears(5);
    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        this.color = color;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
}

