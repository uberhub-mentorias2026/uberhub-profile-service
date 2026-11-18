package br.edu.iftm.uberhub_mentorias_profile_service.model;

import java.util.Objects;

// @Arthur
public record VerifiedIdentity(String firebaseUid, String email, boolean emailVerified) {
    public VerifiedIdentity {
        Objects.requireNonNull(firebaseUid, "firebaseUid must not be null");
        email = email != null && email.isBlank() ? null : email;
    }
}
