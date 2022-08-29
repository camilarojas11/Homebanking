package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;
    private String number;
    private Date creationDate;
    private Double balance;
    @ManyToOne(fetch = FetchType.EAGER) //Con esto te va a devolver todas las cuentas que tenga det cliente
    @JoinColumn(name="client_id") //puedo usar el @column, arriba de un atributo particular como lastName, para modificar los nombres de otras columnas, ej que la de lastName se llame "apellido" en español
    private Client client;
    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    Set<Transaction> transactions = new HashSet<>();

    public Account() {
    }

    public Account(String number, Date creationDate, Double balance, Client client) { //Me obliga a tener generado primero el CLIENTE antes q la cuenta, lo agrego como un parametro mas al crear el objeto (ej, pongo client1). en cambio si no lo tengo, debo hacer el metodo add (comentado abajo) para al crear el obj en vez de agregarlo como parametro le voy haciendo un add abajo de cada obj
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    public Set<Transaction> getTransaction() {
        return transactions;
    }

    @Override
    public String toString(){ return "La cuenta " + number + " " + creationDate + " " + balance + " " + id;
    }


}
