package br.edu.iftm.uberhub_mentorias_profile_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.uberhub_mentorias_profile_service.model.VerifiedIdentity;
import br.edu.iftm.uberhub_mentorias_profile_service.port.FirebaseIdentityVerifier;

// @Arthur - Endpoint de identidade: GET /api/profile/me com Authorization: Bearer <Firebase-ID-Token>
@RestController
public class IdentityController {

    private final FirebaseIdentityVerifier identityVerifier;

    public IdentityController(@Autowired(required = false) FirebaseIdentityVerifier identityVerifier) {
        this.identityVerifier = identityVerifier;
    }

    @GetMapping("/api/profile/me")
    public ResponseEntity<VerifiedIdentity> me(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Cabeçalho Authorization: Bearer <Firebase-ID-Token> é obrigatório.");
        }

        String idToken = authorizationHeader.substring(7).trim();

        if (identityVerifier == null) {
            throw new IllegalStateException("Validação do Firebase está desativada (defina firebase.enabled=true).");
        }

        return ResponseEntity.ok(identityVerifier.verify(idToken));
    }
}