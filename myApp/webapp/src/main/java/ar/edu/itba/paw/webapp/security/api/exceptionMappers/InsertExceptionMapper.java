package ar.edu.itba.paw.webapp.security.api.exceptionMappers;

import ar.edu.itba.paw.models.exceptions.InsertException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InsertExceptionMapper implements ExceptionMapper<InsertException> {
    @Override
    public Response toResponse(InsertException e) {
        return ExceptionMapperUtil.parseException(Response.Status.NO_CONTENT, e.getMessage());
    }
}
