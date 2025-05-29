package com.springboot.bankDemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable())
			.authorizeHttpRequests((authorize) -> authorize
					// Customer
					.requestMatchers("/api/customer/post").permitAll()
					.requestMatchers("/api/customer/get-one").hasAuthority("CUSTOMER")
					.requestMatchers("/api/customer/get-all").permitAll()
					.requestMatchers("/api/customer/put").hasAuthority("CUSTOMER")
					// Document
					.requestMatchers("/api/document/post").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/put").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/get-one").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/get-all").permitAll()
					//Beneficiary
					.requestMatchers("/api/beneficiary/post").hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/put").hasAuthority("CUSTOMER")
					// Branch
					.requestMatchers("/api/branch/post").hasAuthority("GM")
					.requestMatchers("/api/branch/get-all").permitAll()
					.requestMatchers("/api/branch/get/ifscCode").permitAll()
					.anyRequest().authenticated()
			 )
			.httpBasic(Customizer.withDefaults());
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager getAuthManager(AuthenticationConfiguration auth) throws Exception {
		return auth.getAuthenticationManager();
	}
}
