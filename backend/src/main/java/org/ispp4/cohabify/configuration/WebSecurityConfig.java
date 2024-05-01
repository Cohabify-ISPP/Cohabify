package org.ispp4.cohabify.configuration;

import java.util.Arrays;

import org.ispp4.cohabify.authentication.JwtAuthenticationFilter;
import org.ispp4.cohabify.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
		http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable()) // TODO: Configure and enable
			.authorizeHttpRequests(requests -> requests
					.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR, DispatcherType.INCLUDE).permitAll()
					.requestMatchers(HttpMethod.OPTIONS).permitAll()
					.requestMatchers("/**").permitAll()
					.requestMatchers("/resources/**","/webjars/**", "/WEB-INF/**", "/static/**").permitAll()
					.requestMatchers("/api/tag/types/USER_TAG").permitAll()
					.requestMatchers("/chat-ws").permitAll()
					.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register", "/auth/login/google", "/auth/reset-password").anonymous()
					.requestMatchers(HttpMethod.GET, "/auth/register/verify/**", "/auth/reset-password/**").anonymous()
					.requestMatchers(HttpMethod.POST, "/auth/getUser").authenticated()
					.requestMatchers(HttpMethod.GET, "/api/advertisements/houses", "/api/advertisements/users").permitAll()
					.requestMatchers("/api/**").authenticated()
					.anyRequest().denyAll() 
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(new GoogleAuthenticationProvider())
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

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration(); 
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://localhost:8080", "ws://localhost:8080",
													  "http://localhost:5173", "http://localhost:5173/", "ws://localhost:5173",
													  "https://s3-v2-cohabify.onrender.com", "https://s3-v2-cohabify.onrender.com/",
													  "https://testing-cohabify.onrender.com", "https://testing-cohabify.onrender.com, http://127.0.0.1:5173"));
		configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:8080", "http://localhost:8080", "ws://localhost:8080",
																  "http://localhost:5173", "http://localhost:5173/", "ws://localhost:5173",
																  "https://s3-v2-cohabify.onrender.com", "https://s3-v2-cohabify.onrender.com/",
																  "https://testing-cohabify.onrender.com", "https://testing-cohabify.onrender.com/",
																  "http://127.0.0.1:5173", "http://http://127.0.0.1:5173/"));
		configuration.setAllowedMethods(Arrays.asList("*"));
		configuration.setAllowedHeaders(Arrays.asList("Authentication", "content-type"));
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
	   return source;
	}
    
}
