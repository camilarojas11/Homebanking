package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@SpringBootTest //Para que no me tire error el encoder de la password, ignoro el JPATEST

@AutoConfigureTestDatabase(replace = NONE)

public class RepositoriesTest {



    @Autowired

    LoanRepository loanRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    AccountRepository accountRepository;



    //Loans
//Loans
    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }
    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }
    //Client
    @Test
    public void existClient(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,is(not(empty())));
    }
    @Test
    public void existAdminCient(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients,  hasItem(hasProperty("password", startsWith("{bcrypt}$"))));

    }


    //Card
        @Test
        public void existCard(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards,is(not(empty())));
        }
        @Test
        public void correctNumberCard(){
            List<Card> cards = cardRepository.findAll();
            assertThat(cards,is(hasItem(hasProperty("number", startsWith("8888")))));
        }

    //Account
    @Test
    public void existAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }
    @Test
    public void correctNumberAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(hasItem(hasProperty("number", startsWith("VIN")))));
    }

    //Transaction
    @Test
    public void existTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions,is(not(empty())));
    }
    @Test
    public void existCreditTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("type", is(TransactionType.CREDIT))));
    }




}

