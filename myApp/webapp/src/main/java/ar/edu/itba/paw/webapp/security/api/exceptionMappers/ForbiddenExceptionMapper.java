package ar.edu.itba.paw.webapp.security.api.exceptionMappers;

import ar.edu.itba.paw.webapp.exceptions.ForbiddenException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import javax.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<ForbiddenException> {

    @Override
    public Response toResponse(ForbiddenException e) {
        return ExceptionMapperUtil.parseException(Response.Status.FORBIDDEN, e.getMessage());
    }
}
