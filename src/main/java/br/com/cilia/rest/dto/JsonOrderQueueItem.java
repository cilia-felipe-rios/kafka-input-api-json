package br.com.cilia.rest.dto;

public record JsonOrderQueueItem(String id, OrderInput order, long timestamp) {
}
