package tmdtdemo.tmdt.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import tmdtdemo.tmdt.exception.BaseException;

import java.io.IOException;

public class CsrfTokenLoggerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        String csrfToken = request.getHeader("X-CSRF-TOKEN");
        if(csrfToken == null || !token.getToken().equals(csrfToken)){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"csrf failed");
        }
        System.out.println("csrf token : " + token.getToken());
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getServletPath().equals("/api/v1/account/login") || super.shouldNotFilter(request);
    }

}
