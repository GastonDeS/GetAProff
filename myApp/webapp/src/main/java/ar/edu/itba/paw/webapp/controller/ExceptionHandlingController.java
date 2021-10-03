package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.exceptions.LoginErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@ControllerAdvice
public class ExceptionHandlingController {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(LoginErrorException.class)
    ModelAndView loginErrorException(LoginErrorException e) {
        ModelAndView mav = new ModelAndView("login");
        return mav.addObject("exception", messageSource.getMessage(e.getMessage(), null, Locale.getDefault()));
    }
}
