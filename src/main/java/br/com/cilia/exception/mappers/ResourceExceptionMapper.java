package br.com.cilia.exception.mappers;

import br.com.cilia.dto.ApiResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ResourceExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, Status.BAD_REQUEST.getStatusCode(), e.getMessage()))
                    .build();
        }

        if (e.getCause() instanceof JsonProcessingException) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ApiResponse(false, Status.BAD_REQUEST.getStatusCode(), "the request body is not a valid json"))
                    .build();
        }

        return Response
                .status(Status.INTERNAL_SERVER_ERROR)
                .entity(new ApiResponse(false, Status.INTERNAL_SERVER_ERROR.getStatusCode(), e.getMessage()))
                .build();
    }
}
