package com.example.rgpdplatform.config.jwt;

import com.example.rgpdplatform.handlers.ErrorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedEntryPoint implements AccessDeniedHandler {
    private final ObjectMapper om;

    public AccessDeniedEntryPoint() {
        this.om = new ObjectMapper();
        this.om.setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL);
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ServletOutputStream out = response.getOutputStream();
        om.writeValue(
                out,
                ErrorDto
                        .builder()
                        .httpCode(403)
                        .message("Vous n'avez pas accès à cette fonctionnalité!")
                        .build()
        );
        out.flush();
    }
}
