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
    private long id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    /**
     * Descripción: Nos permite traer los datos de préstamos de cada cliente
     * @return Este metodo retorna una colección de tipo Set de Loans
     */
/*
    public Set<Loan> getLoans(){
        return clientLoans.stream().map(clientLoan -> clientLoan.getLoan()).collect(Collectors.toSet()); //map para mutarlos objetos sobre los q itera
    } //no permite repetidos y los ordena / getloans obtiene los prestamos
*/


    public void setAccounts(Set<Account> accounts) { //void no devuelve nada, no tiene return, solo obtiene datos
        this.accounts = accounts;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
    /*
    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }
        (no hace falta este bloque porque tenemos el client en el constructor, si queremos dejarlo debemos borrar el client del constructor de account)

     */


    @Override
    public String toString() {
        return "El cliente " + firstName + " " + lastName + " " + email;
    }

}