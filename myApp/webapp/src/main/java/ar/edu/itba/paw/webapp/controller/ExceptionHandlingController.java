package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.exceptions.ClassNotFoundException;
import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.security.api.models.ApiErrorDetails;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.core.Response;

@ControllerAdvice
public class ExceptionHandlingController {

    @ExceptionHandler(NoUserLoggedException.class)
    public Response loginErrorException(RuntimeException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler({ProfileNotFoundException.class,
            OperationFailedException.class,
            InvalidOperationException.class,
            InvalidParameterException.class})
    public Response errorException(RuntimeException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public Response classNotFoundException(ClassNotFoundException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({NoContentException.class, ImageNotFoundException.class})
    public Response noContentException(ImageNotFoundException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.NO_CONTENT, exception.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public Response ConflictException(ConflictException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.CONFLICT, exception.getMessage());
    }

    private Response getExceptionResponseByStatusAndMessage(Response.Status status, String message) {
        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(message);
        return Response.status(status).entity(errorDetails).build();
    }
}
