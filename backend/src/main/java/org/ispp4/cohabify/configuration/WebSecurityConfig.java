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
					.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
					.requestMatchers(HttpMethod.GET,"/api/user/list", "/api/user/owners").authenticated()
					.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "^/api/user/[0-9a-fA-F]+$")).authenticated()
					.requestMatchers(HttpMethod.POST,"/api/user/add").authenticated()
					.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.PUT,"^/api/user/update/[0-9a-fA-F]+$")).authenticated()
					.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.DELETE,"^/api/user/delete/[0-9a-fA-F]+$")).authenticated()
					.requestMatchers(HttpMethod.GET,"/api/tag/list").authenticated()
					.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.GET, "^/api/tag/[0-9a-fA-F]+$"),
									 RegexRequestMatcher.regexMatcher(HttpMethod.GET, "^/api/tag/types/[0-9a-zA-Z_]+$")).authenticated()
					.requestMatchers(HttpMethod.POST,"/api/tag/add").authenticated()
					.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.PUT,"^/api/tag/update/[0-9a-fA-F]+$")).authenticated()
					.requestMatchers(RegexRequestMatcher.regexMatcher(HttpMethod.DELETE,"^/api/tag/delete/[0-9a-fA-F]+$")).authenticated()
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
