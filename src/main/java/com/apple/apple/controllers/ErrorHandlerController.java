package com.apple.apple.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController {
    
    @ExceptionHandler({ArithmeticException.class})
    public String aritmeticaError(Exception e, Model model) {
        model.addAttribute("error","Error de aritmetica");
        model.addAttribute("message",e.getMessage());
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error/aritmetica";
    } 
}
