package com.springboot.bankDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf((csrf) -> csrf.disable())
			.authorizeHttpRequests((authorize) -> authorize
					// Account
					.requestMatchers("/api/account/post/").permitAll()
					.requestMatchers("/api/account/post/{customerId}/").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/account/get-all").hasAuthority("MANAGER")
					.requestMatchers("/api/account/put/status/{accountId}/").hasAuthority("EXECUTIVE")
					.requestMatchers("/api/account/put/balance/{accountId}").permitAll()
					.requestMatchers("/api/account/get-one").permitAll()//..hasAuthority("CUSTOMER")
					.requestMatchers("/api/account/get/id/{accountId}").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/account/get/customerId/{customerId}").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/account/get/{branchId}").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/account/get/status").hasAnyAuthority("EXECUTIVE","MANAGER")
					// AccountType
					.requestMatchers("/api/accountType/post").hasAuthority("MANAGER")
					.requestMatchers("/api/accountType/put/{id}").hasAuthority("MANAGER")
					.requestMatchers("/api/accountType/get-all").permitAll()
					.requestMatchers("/api/accountType/get/type").permitAll()
					.requestMatchers("/api/accountType/get-one/{id}").permitAll()//
					//Beneficiary
					.requestMatchers("/api/beneficiary/post").permitAll()//.hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/put/{id}/{customerId}").permitAll()//.hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/delete/{id}").permitAll()//.hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/get").permitAll()//.hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/get-one/{id}").hasAuthority("CUSTOMER")
					// Branch
					.requestMatchers("/api/branch/post").hasAuthority("MANAGER")
					.requestMatchers("/api/branch/put/{id}").hasAuthority("MANAGER")
					.requestMatchers("/api/branch/get-all").permitAll()
					.requestMatchers("/api/branch/get/ifscCode").permitAll()
					.requestMatchers("/api/branch/get-one/{id}").permitAll()
					// Customer
					.requestMatchers("/api/customer/post").permitAll()
					.requestMatchers("/api/customer/get").permitAll()
					.requestMatchers("/api/customer/get-one/{id}").hasAnyAuthority("EXECUITVE")
					.requestMatchers("/api/customer/get-all").hasAuthority("MANAGER")
					.requestMatchers("/api/customer/put").permitAll()
					// Document
					.requestMatchers("/api/document/post").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/put").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/get").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/get").hasAuthority("EXECUTIVE")
					.requestMatchers("/api/document/get-all").hasAuthority("MANAGER")
					// Enums
					.requestMatchers("/api/enum/executive/roles/get/").permitAll()
					.requestMatchers("/api/enum/loanApply/status/get/").permitAll()
					.requestMatchers("/api/enum/loan/status/get/").permitAll()
					.requestMatchers("/api/enum/loan/type/get/").permitAll()
					.requestMatchers("/api/enum/transfer/type/get").permitAll()
					// Executive
					.requestMatchers("/api/executive/post/{branchId}").permitAll()//.hasAnyAuthority("MANAGER","ADMIN")
					.requestMatchers("/api/executive/put").hasAuthority("EXECUTIVE")
					.requestMatchers("/api/executive/get-one").hasAuthority("EXECUTIVE")
					.requestMatchers("/api/executive/get/{branchId}").hasAuthority("MANAGER")
					.requestMatchers("/api/executive/get-all").hasAuthority("MANAGER")
					// Loan
					.requestMatchers("/api/loan/post").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/loan/put/status/{id}").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/loan/get-by/branchId/{branchId}").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/loan/get-by/id/{id}").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/loan/get-one").permitAll()//.hasAuthority("CUSTOMER")
					.requestMatchers("/api/loan/get-all").hasAuthority("MANAGER")
					// Loan Application
					.requestMatchers("/api/loanApply/post/{loanDetailsId}/{accountId}").permitAll()//.hasAuthority("CUSTOMER")
					.requestMatchers("/api/loanApply/put/status/cancelled/{id}").permitAll()//.hasAuthority("CUSTOMER")
					.requestMatchers("/api/loanApply/put/status/{id}").permitAll()//.hasAnyRole("EXECUTIVE","MANAGER")
					.requestMatchers("/api/loanApply/get-one").permitAll()//.hasAuthority("CUSTOMER")
					.requestMatchers("/api/loanApply/get-by/status").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/loanApply/get-by/branchId/{branchId}").hasAnyAuthority("EXECUTIVE","MANAGER")
					.requestMatchers("/api/loanApply/get-all").hasAuthority("MANAGER")
					// Loan Details
					.requestMatchers("/api/loanDetails/post").permitAll()
					.requestMatchers("/api/loanDetails/get/all").permitAll()
					// Loan Repayment
					.requestMatchers("/api/loanRepay/poat/{laonId}").hasAuthority("CUSTOMER")
					.requestMatchers("/api/loanRepay/get-by/loanId/{laonId}").authenticated()
					// Manager 
					.requestMatchers("/api/manager/post").permitAll()//.hasAuthority("ADMIN")
					.requestMatchers("/api/manager/put").hasAuthority("MANAGER")
					.requestMatchers("/api/manager/get-one").hasAuthority("MANAGER")
					.requestMatchers("/api/manager/get-all").hasAuthority("MANAGER")
					// Transaction
					.requestMatchers("/api/transaction/post/deposit/{accountId}").permitAll()//.authenticated()
					.requestMatchers("/api/transaction/post/withdraw/{accountId}").permitAll()//.authenticated()
					.requestMatchers("/api/transaction/post/transfer/{accountId}/{beneficiaryId}").permitAll()//.authenticated()
					.requestMatchers("/api/transaction/get-btw/{accountId}").permitAll()//.hasAnyAuthority("CUSTOMER","EXECUTIVE","MANAGER")
					.requestMatchers("/api/transaction/get-from/{accountId}").permitAll()//.hasAnyAuthority("CUSTOMER","EXECUTIVE","MANAGER")
					.requestMatchers("/api/transaction/get-10/{accountId}").permitAll()//.hasAnyAuthority("CUSTOMER","EXECUTIVE","MANAGER")
					.requestMatchers("/api/transaction/get-nMonth/{accountId}").hasAnyAuthority("CUSTOMER","EXECUTIVE","MANAGER")
					.requestMatchers("/api/transaction/get/statement/{accountId}").permitAll()//.hasAnyAuthority("CUSTOMER","EXECUTIVE","MANAGER")
					// User
					.requestMatchers("/api/user/signup").permitAll()
					.requestMatchers("/api/user/put/status/{id}").permitAll()
					.requestMatchers("/api/user/get/all").permitAll()
					.requestMatchers("/api/user/token").permitAll()
					.requestMatchers("/api/user/details").permitAll()
					.anyRequest().authenticated()
			 )
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
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
