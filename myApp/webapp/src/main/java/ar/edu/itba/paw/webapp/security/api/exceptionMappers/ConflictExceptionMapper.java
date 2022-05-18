package ar.edu.itba.paw.webapp.security.api.exceptionMappers;

import ar.edu.itba.paw.webapp.exceptions.ConflictException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

    @Override
    public Response toResponse(ConflictException e) {
        return ExceptionMapperUtil.parseException(Response.Status.CONFLICT, e.getMessage());
    }

}
