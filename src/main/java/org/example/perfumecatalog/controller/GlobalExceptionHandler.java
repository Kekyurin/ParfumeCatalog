package org.example.perfumecatalog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class GlobalExceptionHandler {

    private static final String MESSAGE = "message";

    @ResponseStatus(value = HttpStatus.BAD_REQUEST,
            reason = "Something gone wrong")
    @ExceptionHandler(RuntimeException.class)
    public String conflict(Model model, Exception ex) {
        model.addAttribute(MESSAGE, ex.getMessage());
        return "error";
    }
}
