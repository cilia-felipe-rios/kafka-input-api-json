package br.com.cilia.dto;

public record JsonOrderQueueItem(String id, OrderInput order, long timestamp) {
}
