package tmdtdemo.tmdt.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tmdtdemo.tmdt.exception.BaseException;
import tmdtdemo.tmdt.utils.HelperUtils;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        BaseException exception = new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()),"authentication info wrong");

        String json = HelperUtils.JSON_WRITTER.writeValueAsString(exception);

        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(json);
        log.error("authentication info error .....");
    }
}
