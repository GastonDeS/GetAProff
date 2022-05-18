package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.exceptions.*;
import ar.edu.itba.paw.webapp.security.api.models.ApiErrorDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.core.Response;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(NoUserLoggedException.class)
//    public ResponseEntity<ApiErrorDetails> loginErrorException(RuntimeException exception) {
//        return getExceptionResponseByStatusAndMessage(Response.Status.UNAUTHORIZED, exception.getMessage());
//    }

//    @ExceptionHandler({
//            OperationFailedException.class,
//            InvalidOperationException.class,
//            InvalidParameterException.class})
//    public ResponseEntity<ApiErrorDetails> errorException(RuntimeException exception) {
//        return getExceptionResponseByStatusAndMessage(Response.Status.FORBIDDEN, exception.getMessage());
//    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ApiErrorDetails> notFoundException(NotFoundException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({NoContentException.class})
    public ResponseEntity<ApiErrorDetails> noContentException(NoContentException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.NO_CONTENT, exception.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ApiErrorDetails> conflictException(ConflictException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.CONFLICT, exception.getMessage());
    }

    @ExceptionHandler({InvalidParameterException.class, InvalidOperationException.class})
    public ResponseEntity<ApiErrorDetails> badRequestException(RuntimeException exception) {
        return getExceptionResponseByStatusAndMessage(Response.Status.BAD_REQUEST, exception.getMessage());
    }

    private ResponseEntity<ApiErrorDetails> getExceptionResponseByStatusAndMessage(Response.Status status, String message) {
        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(message);
        return ResponseEntity.status(errorDetails.getStatus()).body(errorDetails);
    }
}
