package br.com.cilia.rest.service;

import br.com.cilia.rest.dto.RestOrderQueueItem;
import br.com.cilia.rest.dto.OrderInput;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class RestInputService {

    @Inject
    @Channel("orders-rest-raw")
    Emitter<RestOrderQueueItem> emitter;

    public void createOrder(OrderInput orderInput) throws IllegalArgumentException {
        RestOrderQueueItem restOrderQueueItem = new RestOrderQueueItem(UUID.randomUUID().toString(), orderInput, Instant.now().toEpochMilli());
        emitter.send(restOrderQueueItem);
    }
}
