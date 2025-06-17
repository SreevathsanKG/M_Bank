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
					.requestMatchers("/api/account/post/").hasAuthority("CUSTOMER")
					.requestMatchers("/api/account/post/{customerId}/").hasAnyAuthority("CUSTOMER_EXECUTIVE","MANAGER","GM")
					.requestMatchers("/api/account/get-all").hasAuthority("GM")
					.requestMatchers("/api/account/put/status/{accountId}/").hasAnyAuthority("CUSTOMER_EXECUTIVE","GM")
					.requestMatchers("/api/account/put/balance/{accountId}").permitAll()
					.requestMatchers("/api/account/get-one").hasAuthority("CUSTOMER")
					.requestMatchers("/api/account/get/id/{accountId}").hasAnyAuthority("CUSTOMER_EXECUTIVE","MANAGER")
					.requestMatchers("/api/account/get/customerId/{customerId}").hasAnyAuthority("CUSTOMR_EXECUTIVE","MANAGER")
					.requestMatchers("/api/account/get/{branchId}").hasAnyAuthority("CUSTOMER_EXECUTIVE","MANAGER")
					.requestMatchers("/api/account/get/status").hasAnyAuthority("CUSTOMER_EXECUTIVE","MANAGER")
					// AccountType
					.requestMatchers("/api/accountType/post").hasAnyAuthority("MANAGER","GM")
					.requestMatchers("/api/accountType/put/{id}").hasAnyAuthority("MANAGER","GM")
					.requestMatchers("/api/accountType/get-all").permitAll()
					.requestMatchers("/api/accountType/get/type").permitAll()
					.requestMatchers("/api/accountType/get-one/{id}").permitAll()
					//Beneficiary
					.requestMatchers("/api/beneficiary/post").hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/put").hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/delete/{id}").hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/get").hasAuthority("CUSTOMER")
					.requestMatchers("/api/beneficiary/get-one/{id}").hasAuthority("CUSTOMER")
					// Branch
					.requestMatchers("/api/branch/post").hasAuthority("GM")
					.requestMatchers("/api/branch/put/{id}").hasAuthority("GM")
					.requestMatchers("/api/branch/get-all").permitAll()
					.requestMatchers("/api/branch/get/ifscCode").permitAll()
					.requestMatchers("/api/branch/get-one/{id}").permitAll()
					// Customer
					.requestMatchers("/api/customer/post").permitAll()
					.requestMatchers("/api/customer/get").hasAuthority("CUSTOMER")
					.requestMatchers("/api/customer/get-one/{id}").hasAnyAuthority("CUSTOMER_EXECUTIVE","LOAN_EXECUTIVE","FINANCE_EXECUITVE")
					.requestMatchers("/api/customer/get-all").hasAuthority("GM")
					.requestMatchers("/api/customer/put").hasAuthority("CUSTOMER")
					// Document
					.requestMatchers("/api/document/post").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/put").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/get").hasAuthority("CUSTOMER")
					.requestMatchers("/api/document/get").hasAuthority("CUSTOMER_EXECUTIVE")
					.requestMatchers("/api/document/get-all").hasAuthority("GM")
					// Enums
					.requestMatchers("/api/enum/executive/roles/get/").permitAll()
					.requestMatchers("/api/enum/loanApply/status/get/").permitAll()
					.requestMatchers("/api/enum/loan/status/get/").permitAll()
					.requestMatchers("/api/enum/loan/type/get/").permitAll()
					.requestMatchers("/api/enum/transfer/type/get/").permitAll()
					// Executive
					.requestMatchers("/api/executive/post/{branchId}/").hasAnyAuthority("MANAGER","GM")
					.requestMatchers("/api/executive/put").hasAnyAuthority("CUSTOMER_EXECUTIVE","LOAN_EXECUTIVE","FINANCE_EXECUTIVE")
					.requestMatchers("/api/executive/get-one").hasAnyAuthority("CUSTOMER_EXECUTIVE","LOAN_EXECUTIVE","FINANCE_EXECUTIVE")
					.requestMatchers("/api/executive/get/{branchId}").hasAnyAuthority("MANAGER","GM")
					.requestMatchers("/api/executive/get-all").hasAuthority("GM")
					// Loan
					.requestMatchers("/api/loan/post/{loanApplicationId}").hasAnyAuthority("FINANCE_EXECUTIVE","MANAGER")
					.requestMatchers("/api/loan/put/status/{id}").hasAnyAuthority("FINANCE_EXECUTIVE","MANAGER")
					.requestMatchers("/api/loan/get-by/branchId/{branchId}").hasAnyAuthority("FINANCE_EXECUTIVE","MANAGER")
					.requestMatchers("/api/loan/get-by/id/{id}").hasAnyAuthority("FINANCE_EXECUTIVE","MANAGER")
					.requestMatchers("/api/loan/get-all").hasAuthority("GM")
					// Loan Application
					.requestMatchers("/api/loanApply/post").hasAuthority("CUSTOMER")
					.requestMatchers("/api/loanApply/put/status/cancelled/{id}").hasAuthority("CUSTOMER")
					.requestMatchers("/api/loanApply/put/status/{id}").hasAnyRole("FINANCE_EXECUTIVE","MANAGER")
					.requestMatchers("/api/loanApply/get-one/status?status=APPROVED").hasAuthority("CUSTOMER")
					.requestMatchers("/api/loanApply/get-by/status").hasAnyAuthority("FINANCE_EXECUTIVE","MANAGER")
					.requestMatchers("/api/loanApply/get-by/branchId/{branchId}").hasAnyAuthority("FINANCE_EXECUTIVE","MANAGER")
					.requestMatchers("/api/loanApply/get-all").hasAnyAuthority("GM")
					// Loan Repayment
					.requestMatchers("/api/loanRepay/poat/{laonId}").hasAuthority("CUSTOMER")
					.requestMatchers("/api/loanRepay/get-by/loanId/{laonId}").authenticated()
					// Manager 
					.requestMatchers("/api/manager/post/{branchId}").hasAuthority("GM")
					.requestMatchers("/api/manager/put").hasAuthority("MANAGER")
					.requestMatchers("/api/manager/get-one").hasAuthority("MANAGER")
					.requestMatchers("/api/manager/get-all").hasAuthority("GM")
					// Transaction
					.requestMatchers("/api/transaction/post/deposit/{accountId}").authenticated()
					.requestMatchers("/api/transaction/post/withdraw/{accountId}").authenticated()
					.requestMatchers("/api/transaction/post/transfer/{accountId}/{beneficiaryId}").authenticated()
					.requestMatchers("/api/transaction/get-btw/{accountId}").hasAnyAuthority("CUSTOMER","FINANCE_EXECUTIVE","MANAGER","GM")
					.requestMatchers("/api/transaction/get-from/{accountId}").hasAnyAuthority("CUSTOMER","FINANCE_EXECUTIVE","MANAGER","GM")
					.requestMatchers("/api/transaction/get-10/{accountId}").hasAnyAuthority("CUSTOMER","FINANCE_EXECUTIVE","MANAGER","GM")
					.requestMatchers("/api/transaction/get-nMonth/{accountId}").hasAnyAuthority("CUSTOMER","FINANCE_EXECUTIVE","MANAGER","GM")
					.requestMatchers("/api/transaction/get/statement/{accountId}").hasAnyAuthority("CUSTOMER","FINANCE_EXECUTIVE","MANAGER","GM")
					// User
//					.requestMatchers("/api/user/signup").permitAll()
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
