package br.com.cilia.rest.security;

import br.com.cilia.rest.dto.ApiResponse;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

@Provider
public class AuthTokenValidationFilter implements ContainerRequestFilter {

    private static final String EXPECTED_HEADER = "auth_token";
    private static final String EXPECTED_TOKEN = "VALID_TOKEN";

    @Override
    public void filter(ContainerRequestContext requestContext) {
        String value = requestContext.getHeaderString(EXPECTED_HEADER);
        String errorMessage = null;

        if (value == null) {
            errorMessage = "authorization header not found";

        } else if (!EXPECTED_TOKEN.equals(value)) {
            errorMessage = "invalid access token";
        }

        if (errorMessage != null) {
            Response abortResponse = Response.status(Status.UNAUTHORIZED)
                    .entity(new ApiResponse(false, Status.UNAUTHORIZED.getStatusCode(), errorMessage))
                    .build();

            requestContext.abortWith(abortResponse);
        }
    }
}
