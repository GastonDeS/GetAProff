package ar.edu.itba.paw.webapp.security.api.exceptionMappers;

import ar.edu.itba.paw.webapp.exceptions.NoContentException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoContentExceptionMapper implements ExceptionMapper<NoContentException> {

    @Override
    public Response toResponse(NoContentException e) {
        return ExceptionMapperUtil.parseException(Response.Status.NO_CONTENT, e.getMessage());
    }

}
