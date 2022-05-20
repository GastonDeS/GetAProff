package ar.edu.itba.paw.webapp.security.api.exceptionMappers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        return ExceptionMapperUtil.parseException(Response.Status.INTERNAL_SERVER_ERROR);
    }
}
