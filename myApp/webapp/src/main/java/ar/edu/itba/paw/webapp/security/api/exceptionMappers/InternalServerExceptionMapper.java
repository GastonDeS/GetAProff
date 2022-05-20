package ar.edu.itba.paw.webapp.security.api.exceptionMappers;

import ar.edu.itba.paw.webapp.exceptions.InternalServerErrorException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InternalServerExceptionMapper implements ExceptionMapper<InternalServerErrorException> {

    @Override
    public Response toResponse(InternalServerErrorException e) {
        return ExceptionMapperUtil.parseException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}
