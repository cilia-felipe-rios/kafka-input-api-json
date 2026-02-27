package br.com.cilia.rest.dto;

public record RestOrderQueueItem(String id, OrderInput order, long timestamp) {
}
