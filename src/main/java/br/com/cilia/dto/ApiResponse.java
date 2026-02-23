package br.com.cilia.dto;

public record ApiResponse(boolean success, Integer code, String message) {}
