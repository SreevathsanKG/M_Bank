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
					.requestMatchers("/api/customer/get-all").hasAuthority("GM")
					.requestMatchers("/api/customer/put").hasAuthority("CUSTOMER")
					// Document
					.requestMatchers("/api/document/post").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/put").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/get-one").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/get-all").hasAuthority("GM")
					//Beneficiary
					.requestMatchers("/api/beneficiary/post").hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/put").hasAuthority("CUSTOMER")
					// Branch
					.requestMatchers("/api/branch/post").hasAuthority("GM")
					.requestMatchers("/api/branch/get-all").permitAll()
					.requestMatchers("/api/branch/get/ifscCode").permitAll()
					// Manager 
					.requestMatchers("/api/manager/post/{branchId}").hasAuthority("GM")
					.requestMatchers("/api/manager/put").hasAuthority("MANAGER")
					.requestMatchers("/api/manager/get-one").hasAuthority("MANAGER")
					.requestMatchers("/api/manager/get-all").hasAuthority("GM")
					// CustomerExecutive
					.requestMatchers("api/ce/post/{branchId}").hasAnyAuthority("MANAGER","GM")
					.requestMatchers("/api/ce/put").hasAuthority("CUSTOMER_EXECUTIVE")
					.requestMatchers("/api/ce/get-one").hasAuthority("CUSTOMER_EXECUTIVE")
					.requestMatchers("/api/ce/get-all").hasAuthority("GM")
					.requestMatchers("/api/ce/get/{branchId}").hasAnyAuthority("MANAGER","GM")
					
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
