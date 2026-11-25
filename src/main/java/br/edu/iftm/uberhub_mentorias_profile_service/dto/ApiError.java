package br.edu.iftm.uberhub_mentorias_profile_service.dto;

import java.time.Instant;

// @Arthur - Formato padrão de erro esperado pelos apps mobile (seção 5 da documentação)
public record ApiError(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp) {
    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(status, error, message, path, Instant.now());
    }
}