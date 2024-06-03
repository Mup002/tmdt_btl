package tmdtdemo.tmdt.config.filter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;


@Configuration
@EnableWebSecurity

public class CorsFilterConfig {
    private static final String GET = "GET";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    private static final Long MAX_AGE = 3600L;
    private static final int CORS_FILTER_ORDER = -102;
    private static final String[] headerAccept = {
            "Authorization",
            "Content-Type",
            "x-client-username",
            "x-admin-username"
    };

    private static final String[] originAccept = {
            "http://localhost:5173",
            "http://localhost:3000",
            "http://localhost:9200",
            "http://localhost:5601"
    };

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList(originAccept));
        config.setAllowedMethods(Collections.singletonList("*"));
        config.setAllowedHeaders(Arrays.asList(headerAccept));
        config.setMaxAge(MAX_AGE);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",config);
        return source;
    }
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        CorsFilter corsFilter = new CorsFilter(corsConfigurationSource());
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(corsFilter);
        bean.setOrder(CORS_FILTER_ORDER);
        return bean;
    }

}
