package br.com.cilia.rest.dto;

public record ApiResponse(boolean success, Integer code, String message) {}
