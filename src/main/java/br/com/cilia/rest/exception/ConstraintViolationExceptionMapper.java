package br.com.cilia.rest.exception;

import br.com.cilia.rest.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException e) {
        String message = e.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + " " + v.getMessage())
                .findFirst()
                .orElse("validation error");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ApiResponse(false, 400, message))
                .build();
    }
}
