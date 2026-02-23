# input-api-json

REST API for ingesting JSON order payloads. Part of the [Kafka PoC - Order Processing System](https://github.com/cilia-felipe-rios/kafka-input-api-json/blob/master/README.md).

## Overview

This service receives order requests via REST, performs basic validation (schema, required fields), and publishes raw orders to the `orders-json-raw` Kafka topic for downstream transformation.

## API

### Create Order

`POST /api/orders`

See [payload specification](https://github.com/cilia-felipe-rios/kafka-poc/blob/main/docs/payloads.md) for request format.

**Responses:**
- `202 Accepted` — Order accepted and queued for processing
- `400 Bad Request` — Validation failed (missing fields, invalid format)
- `401 Unauthorized` — Invalid or missing auth token

## Tech Stack

- Quarkus 3.x (native build)
- SmallRye Reactive Messaging (Kafka)
- SmallRye JWT (authentication)

## Running Locally

```bash
# Dev mode with live reload
./mvnw quarkus:dev

# Build native executable
./mvnw package -Dnative
```

## Configuration

| Property | Default | Description |
|----------|---------|-------------|
| `KAFKA_BOOTSTRAP_SERVERS` | `localhost:9092` | Kafka broker address |
| `QUARKUS_HTTP_PORT` | `8081` | HTTP server port |

## Docker

Build and run via docker-compose from the parent project:

```bash
docker-compose up -d input-api-json
```

Or build standalone:

```bash
docker build -f src/main/docker/Dockerfile.native-multistage -t input-api-json .
docker run -p 8081:8081 -e KAFKA_BOOTSTRAP_SERVERS=host.docker.internal:9092 input-api-json
```
