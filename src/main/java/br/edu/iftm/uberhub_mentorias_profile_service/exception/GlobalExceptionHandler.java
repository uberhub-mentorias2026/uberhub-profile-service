package br.edu.iftm.uberhub_mentorias_profile_service.exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.edu.iftm.uberhub_mentorias_profile_service.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;

// @Arthur - Tratamento global de exceções do profile-service
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 Not Found (ex: profile_not_found, tag_not_found)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> handleNotFound(NoSuchElementException ex, HttpServletRequest request) {
        String code = ex.getMessage() != null ? ex.getMessage() : "not_found";
        ApiError body = ApiError.of(
                HttpStatus.NOT_FOUND.value(),
                code,
                traduzirMensagem(code),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // 400 Bad Request (ex: profile_already_exists, tag_already_exists,
    // invalid_status)
    @ExceptionHandler({ IllegalStateException.class, IllegalArgumentException.class })
    public ResponseEntity<ApiError> handleBadRequest(RuntimeException ex, HttpServletRequest request) {
        String code = ex.getMessage() != null ? ex.getMessage() : "bad_request";
        ApiError body = ApiError.of(
                HttpStatus.BAD_REQUEST.value(),
                code,
                traduzirMensagem(code),
                request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    private String traduzirMensagem(String code) {
        return switch (code) {
            case "profile_already_exists" -> "Já existe um perfil de mentor cadastrado para este usuário.";
            case "profile_not_found" -> "Perfil de mentor não encontrado.";
            case "tag_already_exists" -> "Já existe uma tag cadastrada com este nome.";
            case "tag_not_found" -> "Tag de especialização não encontrada.";
            case "tag_name_required" -> "O nome da tag é obrigatório.";
            case "invalid_status" -> "Status inválido. Use APPROVED ou REJECTED.";
            default -> code;
        };
    }
}