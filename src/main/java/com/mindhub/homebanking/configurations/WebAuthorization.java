package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//Quien accede a qué? Se define a continuación:
@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    @Override //para sobreescribir un metodo q ya existe
    protected void configure(HttpSecurity http) throws Exception {  //Vamos a sobreescribir el método configure.


        http.authorizeRequests()
                .antMatchers("/web/index.html").permitAll()
                .antMatchers("/web/css/**", "/web/img/**", "/web/js/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients").permitAll() //solo le da permiso al POST para acceder a la ruta
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/**").hasAuthority("CLIENT") // asteriscos significan q tiene acceso a todo
                .antMatchers("api/clients/current/accounts"). hasAuthority("CLIENT") //le da permisos a todo
                .antMatchers("api/clients/current/cards"). hasAuthority("CLIENT");

        http.formLogin().usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        http.logout().logoutUrl("/api/logout"); //borra la cookie

        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();


        http.csrf().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if login is successful, just clear the flags asking for authentication
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // if login fails, just send an authentication failure response
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // if logout is successful, just send a success response
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());

        //AGREGAMOS EN EL PROPERTIES EL LIMITE DE TIEMPO DE EXPIRACION DEL TOKEN
        http.sessionManagement().invalidSessionUrl("/web/index.html"); //Te redirije al index al recargar o hacer click en otro lado desp de expirar
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}