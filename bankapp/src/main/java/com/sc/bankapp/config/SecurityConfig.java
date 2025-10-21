package com.sc.bankapp.config;

import com.sc.bankapp.service.CustomerDetailsSerivce;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    //Provider UserDetails Service
    @Bean
    public UserDetailsService userDetailsService(CustomerDetailsSerivce cs) {
        return  cs;
    }


    // Encode password
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    // Authentication provider
    @Bean
    public DaoAuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return  authProvider;
    }


    //security filterchain
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws  Exception{

        http
//                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .csrf( csrf -> csrf.disable()) // disable for API (only for dev/demo; secure appropriately in prod)
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/actuator/**").permitAll()
                                .requestMatchers("/api/customers/register").permitAll()
                                .anyRequest().authenticated()

                )
                .httpBasic(Customizer.withDefaults());

        http.authenticationProvider(
                authenticationProvider(
                        userDetailsService(
                                new CustomerDetailsSerivce()
                        ),
                        passwordEncoder()
                )
        );

        return http.build();
    }


}



















