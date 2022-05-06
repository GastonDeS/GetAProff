package ar.edu.itba.paw.webapp.security.api;

import ar.edu.itba.paw.webapp.security.api.exceptions.ExpiredAuthenticationTokenException;
import ar.edu.itba.paw.webapp.security.api.exceptions.InvalidAuthenticationTokenException;
import ar.edu.itba.paw.webapp.security.api.models.ApiErrorDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFailureMapper {
    private AuthenticationFailureMapper() {}

    public static void handleFailure(HttpServletRequest request, HttpServletResponse response,
                                     AuthenticationException exception, ObjectMapper mapper) throws IOException {

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ApiErrorDetails errorDetails = new ApiErrorDetails();

        if (exception instanceof InvalidAuthenticationTokenException ||
                exception instanceof InsufficientAuthenticationException) {
            response.addHeader("WWW-Authenticate", "Basic realm=\"myRealm\"");
            response.addHeader("WWW-Authenticate", "Bearer token");
        } else if(exception instanceof ExpiredAuthenticationTokenException) {
            response.addHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
        }

        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setStatus(status.value());
        errorDetails.setPath(request.getRequestURI());
        response.setStatus(status.value());
        response.setContentType("application/vnd.campus.api.v1+json");
        mapper.writeValue(response.getWriter(), errorDetails);
    }
}
