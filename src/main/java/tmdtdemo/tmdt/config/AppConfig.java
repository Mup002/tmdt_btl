package tmdtdemo.tmdt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import tmdtdemo.tmdt.config.filter.*;
import tmdtdemo.tmdt.config.exception.CustomAccessDeniedHandler;
import tmdtdemo.tmdt.config.filter.repository.CustomCsrfTokenRepository;
import tmdtdemo.tmdt.jwt.JwtConfig;
import tmdtdemo.tmdt.jwt.JwtService;
import tmdtdemo.tmdt.service.BaseRedisService;

@Configuration
@EnableWebSecurity
public class AppConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
    @Autowired
    JwtConfig jwtConfig;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BaseRedisService baseRedisService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private  CustomAuthenEntryPoint authenEntryPoint;


    @Autowired
    public void configGlobal(final AuthenticationManagerBuilder auth){
        auth.authenticationProvider(customAuthenticationProvider);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        AuthenticationManager manager  = builder.build();
        http
                .cors()
                .and()
//                .csrf(c -> {
//                    c.csrfTokenRepository(new CustomCsrfTokenRepository())
//                            .ignoringRequestMatchers("/api/v1/account/login");
//                })
//                .addFilterAfter(new CsrfTokenLoggerFilter(), CsrfFilter.class)
                .csrf().disable()
                .formLogin().disable()
                .authorizeHttpRequests()
                .requestMatchers(RequestURL.UN_SECURED_URLs).permitAll()
                .requestMatchers(RequestURL.SECURED_URLS_ROLE_USER).hasAnyAuthority("USER","ADMIN")
                .requestMatchers(RequestURL.SECURED_URLS_ROLE_ADMIN).hasAnyAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .authenticationManager(manager)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenEntryPoint)
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtUsernamePasswordAuthenticationFilter(manager,jwtConfig,jwtService, baseRedisService), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig,jwtService,baseRedisService), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
