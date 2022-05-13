package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.services.UserService;
import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.core.Response;

@ControllerAdvice
public class ExceptionHandlingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserService userService;

    @ExceptionHandler(NoUserLoggedException.class)
    public Response loginErrorException(RuntimeException exception) {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @ExceptionHandler({ProfileNotFoundException.class,
            OperationFailedException.class,
            InvalidOperationException.class,
            InvalidParameterException.class})
    public Response errorException(RuntimeException exception) {
    return Response.status(Response.Status.FORBIDDEN).build();
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public Response classNotFoundException(ClassNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
