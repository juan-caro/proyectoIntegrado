package com.example.chesstournaments.controllers;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;

import java.util.Map;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<Map<String, Object>> handleError(WebRequest webRequest) {
        DefaultErrorAttributes errorAttributes = new DefaultErrorAttributes();
        Map<String, Object> errorDetails = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());

        // Puedes personalizar la respuesta de error aquí
        errorDetails.put("message", "Something went wrong");
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
