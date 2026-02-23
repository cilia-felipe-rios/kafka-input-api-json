package br.com.cilia.service;

import br.com.cilia.dto.JsonOrderQueueItem;
import br.com.cilia.dto.OrderInput;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class JsonInputService {

    @Inject
    @Channel("json-input")
    Emitter<JsonOrderQueueItem> emitter;

    public void createOrder(OrderInput orderInput) throws IllegalArgumentException {
        JsonOrderQueueItem jsonOrderQueueItem = new JsonOrderQueueItem(UUID.randomUUID().toString(), orderInput, Instant.now().toEpochMilli());
        emitter.send(jsonOrderQueueItem);
    }
}
