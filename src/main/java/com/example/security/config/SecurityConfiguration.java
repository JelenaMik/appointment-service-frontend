package com.example.security.config;
//
//import jakarta.servlet.Filter;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfiguration {
//
//    private final JwtAuthenticationFilter jwtAuthFilter;
//    private final AuthenticationProvider authenticationProvider;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf()
//                .disable()
//                .authorizeHttpRequests()
//                .requestMatchers("/api/v1/auth/**")
//                .permitAll()
//                .requestMatchers("/api/v1/user/**").permitAll()
////                .requestMatchers("/", "/login/**").permitAll()
////                .requestMatchers(antMatcher(HttpMethod.POST, "/login-process/**"))
////                .permitAll()
//                .requestMatchers("/swagger-ui/**", "**/v3/api-docs/**")
//                .permitAll()
//                .anyRequest()
////                .authenticated()
//                .permitAll()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
////                .exceptionHandling()
////                .accessDeniedHandler(new JwtFilterAccessDenied())
////                .and()
////                .formLogin(form -> form
////                        .loginPage("/login")
//////                        .defaultSuccessUrl("/set-localstorage", true)
////                        .permitAll())
//
////                .formLogin()
////                .loginPage("/login")
//////                .loginProcessingUrl("/my-profile")
//////                .failureUrl("/login")
////                .and()
//
//                .authenticationProvider(authenticationProvider)
//                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//
//}
