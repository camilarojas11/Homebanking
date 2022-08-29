package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Client;

import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<AccountDTO> accounts; //agrego esta lista para que me traiga las cuentas
    private Set<ClientLoanDTO> loans;

    private Set<CardDTO> cards;


    //CLIENTDTO Sirve para mostrarme los datos que YO QUIERO, ej si no quiero mostrar
    //el n° de ID, lo saco de arriba y tb del constructor y listo. El controlador va a
    //venir aca a ver que datos me trae, a traves del repository, sino me va a generar un JSON
    //interminable con todos los datos asociados de cada objeto. CLIENTDTO Limita la cantidad de respuestas a cada llamada
    public ClientDTO(Client client) {
        this.id = client.getId(); //todos los atributos son privados. Por tanto, si pongo client.id solo, no me lo va a traer.
        //Tengo que usar el metodo getId (lo mismo con el resto), que son públicos y están realizados en cada clase.
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.email = client.getEmail();
        this.accounts = client.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());
        this.loans= client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
        this.cards = client.getCards().stream().map(CardDTO::new).collect(Collectors.toSet());
    }

    public Set<CardDTO> getCards() {
        return cards;
    }

    public void setCards(Set<CardDTO> cards) {
        this.cards = cards;
    }

    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public void setLoans(Set<ClientLoanDTO> loans) {
        this.loans = loans;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<AccountDTO> accounts) {
        this.accounts = accounts;
    }


}
