package ar.edu.itba.paw.webapp.security.api.exceptionMappers;

import ar.edu.itba.paw.webapp.security.api.models.ApiErrorDetails;

import javax.ws.rs.core.Response;

public class ExceptionMapperUtil {
    public static Response parseException(Response.Status status, String message) {
        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(message);
        return Response.status(errorDetails.getStatus()).entity(errorDetails).build();
    }

    public static Response parseException(Response.Status status) {
        ApiErrorDetails errorDetails = new ApiErrorDetails();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        return Response.status(errorDetails.getStatus()).entity(errorDetails).build();
    }
}