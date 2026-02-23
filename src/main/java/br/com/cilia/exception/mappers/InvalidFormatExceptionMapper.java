package br.com.cilia.exception.mappers;

import br.com.cilia.dto.ApiResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.stream.Collectors;

@Provider
public class InvalidFormatExceptionMapper implements ExceptionMapper<InvalidFormatException> {

    @Override
    public Response toResponse(InvalidFormatException e) {
        String fieldPath = e.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(Collectors.joining("."));
        String message = String.format("invalid value '%s' for field %s", e.getValue(), fieldPath);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ApiResponse(false, 400, message))
                .build();
    }
}
