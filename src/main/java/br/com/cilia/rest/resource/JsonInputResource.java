package br.com.cilia.rest.resource;

import br.com.cilia.rest.dto.ApiResponse;
import br.com.cilia.rest.dto.OrderInput;
import br.com.cilia.rest.service.JsonInputService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/services/rest/orders")
public class JsonInputResource {

    @Inject
    JsonInputService jsonInputService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ApiResponse createOrder(@Valid @NotNull OrderInput orderInput) {
        jsonInputService.createOrder(orderInput);
        return new ApiResponse(true, 200, "order successfully added to processing queue");
    }
}
