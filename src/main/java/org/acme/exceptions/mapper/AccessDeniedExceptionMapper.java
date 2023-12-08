package org.acme.exceptions.mapper;

import io.netty.handler.codec.http.HttpResponseStatus;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.dto.ExceptionFormat;
import org.acme.exceptions.AccessDeniedException;
import org.acme.exceptions.InvalidPasswordException;

import java.time.LocalDateTime;

@Provider
public class AccessDeniedExceptionMapper implements ExceptionMapper<Exception> {

    /**
     * Exception Mapper for returning Custom exception response format
     * @param exception the exception to map to a response.
     * @return Response
     */
    @Override
    public Response toResponse(Exception     exception) {
        ExceptionFormat exceptionFormat = new ExceptionFormat();
        exceptionFormat.setMessage(exception.getMessage());
        exceptionFormat.setTimestamp(LocalDateTime.now());

        if(exception instanceof AccessDeniedException || exception instanceof InvalidPasswordException) {
            exceptionFormat.setStatus(HttpResponseStatus.UNAUTHORIZED.code());
        }
        return Response.status(exceptionFormat.getStatus()).entity(exceptionFormat).build();
    }
}
