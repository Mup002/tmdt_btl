package tmdtdemo.tmdt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import tmdtdemo.tmdt.auth.UserDetailsServiceCustom;
import tmdtdemo.tmdt.jwt.JwtConfig;

@Configuration
public class BeanConfig {
    @Bean
    public JwtConfig jwtConfig(){
        return new JwtConfig();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceCustom();
    }


}
