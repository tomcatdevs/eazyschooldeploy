package com.eazybytes.example18.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig  {

    @Bean
    SecurityFilterChain SpringSecurityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf()
//                .ignoringRequestMatchers(PathRequest.toH2Console())
                .ignoringRequestMatchers("/saveMsg")
                .ignoringRequestMatchers("/public/**")
                .ignoringRequestMatchers("/api/contact/**")
                .ignoringRequestMatchers("/data-api/**")
                .ignoringRequestMatchers("/eazyschool/actuator/**")
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/dashboard").authenticated()
                .requestMatchers("/displayMessages/**").hasRole("ADMIN")
                .requestMatchers("/closeMsg/**").hasRole("ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/student/**").hasRole("STUDENT")
                .requestMatchers("/api/contact/**").hasRole("ADMIN")
                .requestMatchers("/eazyschool/actuator/**").hasRole("ADMIN")
                .requestMatchers("/","/home").authenticated()
                .requestMatchers("/courses").authenticated()
                .requestMatchers("/data-api/**").authenticated()
                .requestMatchers("/displayProfile").authenticated()
                .requestMatchers("/updateProfile").authenticated()
                .requestMatchers("/contact").permitAll()
                .requestMatchers("/saveMsg").permitAll()
                .requestMatchers("/about").permitAll()
                .requestMatchers("/**").permitAll()
                .requestMatchers("/holidays/**").permitAll()
                .requestMatchers("/eazyschool/actuator/**").permitAll()
                .requestMatchers("/assets/**").permitAll()
//                .requestMatchers("/profile/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/logout").permitAll()
                .requestMatchers("/public/**").permitAll()
//                .requestMatchers("/mobile/**").permitAll()
                .requestMatchers("/holidays/**").permitAll()

//               permitting all paths
//                .requestMatchers("/profile/**").permitAll()
//                .requestMatchers("/courseses/**").permitAll()
//                .requestMatchers("/contacts/**").permitAll()
//                .requestMatchers("/explorer/**").permitAll()

//                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll()
                .and().logout().logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll()
                .and().httpBasic();
//        http.headers().frameOptions().disable();
         return http.build();
    }

//    IN-MEMORY AUTHENTICATION
//   @Bean
//    public InMemoryUserDetailsManager userDetailsManager(){
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("ravina")
//                .password("ravina")
//                .roles("user")
//                .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("prateek")
//                .password("admin")
//                .roles("admin")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }

//    HASHING PLAIN PASSWORD INTO HASH PASSWORD ENCRYPTION
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
