// package io.kp45.mvc;

// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

// @EnableWebSecurity
// public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.authorizeRequests((requests) -> requests.anyRequest().authenticated())
//                 .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
//     }

// }