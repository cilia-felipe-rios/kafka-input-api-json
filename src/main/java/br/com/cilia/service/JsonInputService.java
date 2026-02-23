package br.com.cilia.service;

import br.com.cilia.dto.Payload;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    Emitter<Payload> emitter;

    @Inject
    ObjectMapper objectMapper;

    public void ingest(String jsonInput) throws IllegalArgumentException {
        if (jsonInput == null || jsonInput.isEmpty()) {
            throw new IllegalArgumentException("the payload sent is null or empty");
        }

        if (!isValidJson(jsonInput)) {
            throw new IllegalArgumentException("the payload sent is not a valid json");
        }

        Payload payload = new Payload(UUID.randomUUID().toString(), jsonInput, Instant.now().toEpochMilli());
        emitter.send(payload);
    }

    public boolean isValidJson(String input) {
        try {
            objectMapper.readTree(input);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
