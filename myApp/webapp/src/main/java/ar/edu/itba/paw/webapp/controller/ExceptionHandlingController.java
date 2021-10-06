package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.models.User;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.forms.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@ControllerAdvice
public class ExceptionHandlingController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @ExceptionHandler({LoginErrorException.class,
            NoUserLoggedException.class})
    public ModelAndView loginErrorException(RuntimeException e) {
        ModelAndView mav = new ModelAndView("login");
        return mav.addObject("exception", messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(RegisterErrorException.class)
    public ModelAndView registerErrorException(RegisterErrorException e) {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("exception", messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale()));
        return mav.addObject("register", new RegisterForm());
    }

    @ExceptionHandler({ProfileNotFoundException.class,
            OperationFailedException.class,
            InvalidOperationException.class})
    public ModelAndView errorException(RuntimeException e) {
        Optional<User> curr = userService.getCurrentUser();
        final ModelAndView mav = new ModelAndView("403");
        curr.ifPresent(user -> mav.addObject("uid", user.getId()));
        return mav.addObject("exception", messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale()));
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ModelAndView classNotFoundException(ClassNotFoundException e) {
        return new ModelAndView("redirect:/myClasses?error=true");
    }
}
