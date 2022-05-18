package ar.edu.itba.paw.webapp.security.api.exceptionMappers;

import ar.edu.itba.paw.webapp.exceptions.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    @Override
    public Response toResponse(NotFoundException e) {
        return ExceptionMapperUtil.parseException(Response.Status.NOT_FOUND, e.getMessage());
    }
}
