package com.mindhub.homebanking;
//Importa la clase: (si no aparece debo hacerlo con alt+space sobre Client, luego enter)

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);


	}


	@Autowired
	private PasswordEncoder passwordEncoder; //traigo el metodo para encriptar la contraseña

	@Bean
	public  CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {  																		//passwordEncoder.encode(" ")
			Client client1 = new Client("David", "Cosio", "david@gmail.com", passwordEncoder.encode("david")); //contraseña encriptada
			System.out.println(client1);

			//Este metodo está heredado en JPArepository.java por la padre
			clientRepository.save(client1); //Si no tengo guardado así el objeto, sólo se guarda en la memoria RAM y no hay persistencia (con H2 igual no hay persistencia porque no es una BD real). Cuando tenemos una BD real, se guardan en el disco duro

			Account account1 = new Account("VIN123AA", new Date(), 234.2, client1);
			System.out.println(account1);
			accountRepository.save(account1);
			Account account3 = new Account("VIN123BB", new Date(), 8000.8, client1);
			System.out.println(account3);
			accountRepository.save(account3);
			Client client2 = new Client("Ana", "Marquez", "ana@gmail.com", passwordEncoder.encode("ana")); //passwordencoder sirve para encriptar
			System.out.println(client2);

			clientRepository.save(client2);

			Client client3 = new Client("admin", "admin", "admin@gmail.com", passwordEncoder.encode("admin"));
			clientRepository.save(client3);

			Account account2 = new Account("VIN125AA", new Date(), 500.4, client2);
			System.out.println(account2);
			accountRepository.save(account2);

			Transaction transaction1 = new Transaction(CREDIT, 322.5, "Inmediata", new Date(), account1);
			System.out.println(transaction1);
			transactionRepository.save(transaction1);

			Transaction transaction2 = new Transaction(DEBIT, 6743.4, "Inmediata", new Date(), account1);
			System.out.println(transaction1);
			transactionRepository.save(transaction2);

			//Guarda las opciones de cuotas para cada prestamo:
			List<Integer> paymentsHipotecario = new ArrayList<>();
			paymentsHipotecario.add(12);
			paymentsHipotecario.add(24);
			paymentsHipotecario.add(36);
			paymentsHipotecario.add(48);
			paymentsHipotecario.add(60);

			List<Integer> paymentsPersonal = new ArrayList<>();
			paymentsPersonal.add(6);
			paymentsPersonal.add(12);
			paymentsPersonal.add(24);

			List<Integer> paymentsAutomotriz = new ArrayList<>();
			paymentsAutomotriz.add(6);
			paymentsAutomotriz.add(12);
			paymentsAutomotriz.add(24);
			paymentsAutomotriz.add(36);


			Loan loan1 = new Loan("Hipotecario", 500000.00, paymentsHipotecario);
			Loan loan2 = new Loan("Personal", 100000.00, paymentsPersonal);
			Loan loan3 = new Loan("Automotriz", 300000.00, paymentsAutomotriz);

			loanRepository.save(loan1);
			loanRepository.save(loan2);
			loanRepository.save(loan3);
			/*
			System.out.println(loan1);
			System.out.println(loan2);
			System.out.println(loan3);
			*/
			ClientLoan clientLoan1 = new ClientLoan(5000.9, 6, client1, loan3);

			clientLoanRepository.save(clientLoan1);

			Card card1 = new Card(CardColor.GOLD, CardType.DEBIT, "8888567891234567", 342, client1);
			cardRepository.save(card1);

			Card card2 = new Card(CardColor.SILVER, CardType.CREDIT, "8888567891234567", 342, client1);
			cardRepository.save(card2);

		};

	}


}
