package ar.edu.itba.paw.webapp.controller;

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

    @ExceptionHandler({
            OperationFailedException.class,
            InvalidOperationException.class,
            InvalidParameterException.class})
    public Response errorException(RuntimeException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public Response notFoundException(NotFoundException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({NoContentException.class})
    public Response noContentException(NoContentException exception) {
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
