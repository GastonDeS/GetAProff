package ar.edu.itba.paw.webapp.security.api.exceptionMappers;

import ar.edu.itba.paw.webapp.exceptions.NoUserLoggedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoUserLoggedExceptionMapper implements ExceptionMapper<NoUserLoggedException> {
    @Override
    public Response toResponse(NoUserLoggedException e) {
        return ExceptionMapperUtil.parseException(Response.Status.UNAUTHORIZED, e.getMessage());
    }
}
