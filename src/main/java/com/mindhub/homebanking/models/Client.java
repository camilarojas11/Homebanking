package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

//Anotacion de la API de persistencia que transforma la clase en una tabla
@Entity //Al crear la entidad se genera la tabla. Mapea y con la estructura de la clase
//me genera las tablas de la BD, donde se guardan los datos
public class Client {
    //Configurando ID para crear la tabla y que haya un registro único en la BD
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String firstName;
    private String lastName;
    //@Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER) //el client tiene que ser igual al nombre que le pongo al atributo Private Client client en account
    Set<Account> accounts = new HashSet<>(); //tiene muchas cuentas (es una relacion de uno a muchos, un cliente puede tener una lista de cuentas)

    @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    Set<ClientLoan> clientLoans = new HashSet<>(); //ordena por el id

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    Set<Card> cards = new HashSet<>();
    public Client() {

    }
    //Constructor: (ES UN EJ DE POLIMORFISMO, MISMO METODO SOBREESCRITO DE DISTINTAS MANERAS, POR EJ CUANDO HAY UN CONSTRUCTOR VACIO X DEFECTO Y UNO COMPLETO, HAY POLIMORFISMO)
    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addAccount(Account account){
        account.setClient(this);
        accounts.add(account);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", accounts=" + accounts +
                ", clientLoans=" + clientLoans +
                ", cards=" + cards +
                '}';
    }
}