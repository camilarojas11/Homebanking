package com.mindhub.homebanking.configurations;

import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
//Clase que va a autenticar todos los usuarios:
@Configuration
public class WebAuthentication extends GlobalAuthenticationConfigurerAdapter { //Este ultimo es un objeto que usa spring security para saber como buscará los detalles del usuario

    @Autowired
    ClientRepository clientRepository; //de dónde saca los datos con los que va a trabajar

    @Bean
    public PasswordEncoder passwordEncoder() { //Para encriptar la contraseña (tb lo paso al homebancking y al poner la contraseña en los parametros del obj)
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override //indica que vamos a traer un metodo ya existente y lo vamos a sobreescribir
    public void init(AuthenticationManagerBuilder auth) throws Exception { //metodo init ya existe y nosotros lo sobreescribimos utilizando a Clients, etc
        auth.userDetailsService(inputName-> { //en nuestro caso seria el input de email, es de convención, es un parametro (puede tener cualquier nombre)
            Client client = clientRepository.findByEmail(inputName); //Utiliza el metodo findByEmail del repo para buscar por email al usuario
            if (client != null) { //si existe (lo encuentra)
                return new User(client.getEmail(), client.getPassword(), //new user es una clase de spring security. Aca matchea y se fija que coincidan los valores y existan
                        AuthorityUtils.createAuthorityList("CLIENT")); //¿como saber si un usuario tiene permisos para algo? Por los roles. Client acá sería un rol.
            } else { //no encuentra el usuario                                   //Si encuentra el usuario, lo coloca en el rol de "CLIENT"
                throw new UsernameNotFoundException("Unknown user: " + inputName);
            }
        });
    }
}