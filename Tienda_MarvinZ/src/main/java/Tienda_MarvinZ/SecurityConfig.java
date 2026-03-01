/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Tienda_MarvinZ;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
/**
 *
 * @author marvi
 */


@Configuration
public class SecurityConfig {

    public static final String[] PUBLIC_URLS = {
        "/", "/index", "/fav/**", "/carrito/**", "/consultas/**", "/registro/**",
        "/js/**", "/webjars/**", "/login", "/acceso_denegado"
    };
    public static final String[] USUARIO_URLS = {
        "/facturar/carrito"
    };
    public static final String[] ADMIN_OR_VENDEDOR_URLS = {
        "/producto/listado", "/categoria/listado", "/usuario/listado"
    };
    public static final String[] ADMIN_URLS = {
        "/producto/**", "/categoria/**", "/usuario/**"
    };


    @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(request -> request
        .requestMatchers(PUBLIC_URLS).permitAll()
        .requestMatchers(USUARIO_URLS).hasRole("USER")
        .requestMatchers(ADMIN_OR_VENDEDOR_URLS).hasAnyRole("ADMIN", "VENDEDOR")
        .requestMatchers(ADMIN_URLS).hasRole("ADMIN")
        .anyRequest().authenticated()
    ).formLogin(form -> form
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/", true)
        .failureUrl("/login?error=true")
        .permitAll()
    ).logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout=true")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .permitAll()
    ).exceptionHandling(exceptions -> exceptions
        .accessDeniedPage("/login?error=prohibido")
    ).sessionManagement(session -> session
        .maximumSessions(1)
        .maxSessionsPreventsLogin(false)
    );
    return http.build();
}
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}

//Este método será reemplazado la siguiente semana
 //Este método será reemplazado la siguiente semana
//    @Bean
//    public UserDetailsService users(PasswordEncoder passwordEncoder) {
//        UserDetails juan = User.builder()
//                .username("juan")
//                .password(passwordEncoder.encode("123"))
//                .roles("ADMIN")
//                .build();
//        UserDetails rebeca = User.builder()
//                .username("rebeca")
//                .password(passwordEncoder.encode("456"))
//                .roles("VENDEDOR")
//                .build();
//        UserDetails pedro = User.builder()
//                .username("pedro")
//                .password(passwordEncoder.encode("789"))
//                .roles("USUARIO") // Consistent con tu configuración
//                .build();
//        return new InMemoryUserDetailsManager(juan, rebeca, pedro);
//    }
    @Autowired
    public void configurerGlobal(AuthenticationManagerBuilder build,
            @Lazy PasswordEncoder passwordEncoder,
            @Lazy UserDetailsService userDetailsService) throws Exception {
        build.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
}
