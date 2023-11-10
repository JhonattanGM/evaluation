package cl.evaluation.exercise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;
import cl.evaluation.exercise.security.exceptionhandlings.CustomAuthenticationEntryPoint;
import cl.evaluation.exercise.security.filters.JwtAuthenticationFilter;
import cl.evaluation.exercise.security.filters.JwtAuthorizationFilter;
import cl.evaluation.exercise.security.jwt.JwtUtils;
import cl.evaluation.exercise.services.UserDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserDetailsServiceImpl userDetailsService;
  @Autowired
  JwtAuthorizationFilter authorizationFilter;

  @Autowired
  private CustomAuthenticationEntryPoint authenticationEntryPoint;


  /* Configuracion para entrar a consola h2 sin autorizacion */
  @Bean
  WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(new AntPathRequestMatcher("/h2-console/**"));
  }


  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
      HandlerMappingIntrospector introspector, AuthenticationManager authenticationManager)
      throws Exception {

    JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
    jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
    jwtAuthenticationFilter.setFilterProcessesUrl("/login");


    MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
    return httpSecurity
        .csrf(config -> config.disable())
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers(mvcMatcherBuilder.pattern("/secure")).authenticated();
          auth.requestMatchers(mvcMatcherBuilder.pattern("/**")).permitAll();
        })
        .sessionManagement(session -> {
          session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        })
        .addFilter(jwtAuthenticationFilter)
        .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .build();
  }


  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  AuthenticationManager authenticationManager(HttpSecurity httpSecurity,
      PasswordEncoder passwordEncoder) throws Exception {
    return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
        .userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder)
        .and().build();
  }
}
