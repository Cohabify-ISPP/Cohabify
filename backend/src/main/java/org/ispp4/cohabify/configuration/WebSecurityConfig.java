package org.ispp4.cohabify.configuration;

import org.ispp4.cohabify.authentication.JwtAuthenticationFilter;
import org.ispp4.cohabify.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
    private final CustomUserDetailsService customUserDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(Customizer.withDefaults())
			.csrf(csrf -> csrf.disable()) // TODO: Configure and enable
			.authorizeHttpRequests(requests -> requests
					.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR, DispatcherType.INCLUDE).permitAll()
					.requestMatchers(HttpMethod.OPTIONS).permitAll()
					.requestMatchers("/resources/**","/webjars/**", "/WEB-INF/**").permitAll()
					.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").anonymous()
					.requestMatchers("/api/**").permitAll()
					.anyRequest().denyAll() 
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean 
	PasswordEncoder passwordEncoder() { 
		 return new BCryptPasswordEncoder();  
	}
	
	@Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
}
