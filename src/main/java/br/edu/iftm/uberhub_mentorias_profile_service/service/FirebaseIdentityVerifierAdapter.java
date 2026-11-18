package br.edu.iftm.uberhub_mentorias_profile_service.service;

import br.edu.iftm.uberhub_mentorias_profile_service.model.VerifiedIdentity;
import br.edu.iftm.uberhub_mentorias_profile_service.port.FirebaseIdentityVerifier;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(FirebaseAuth.class)
public class FirebaseIdentityVerifierAdapter implements FirebaseIdentityVerifier {

    private final FirebaseAuth firebaseAuth;

    public FirebaseIdentityVerifierAdapter(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public VerifiedIdentity verify(String idToken) {
        if (idToken == null || idToken.isBlank()) {
            throw new IllegalArgumentException("O token do Firebase é obrigatório.");
        }

        try {
            FirebaseToken token = firebaseAuth.verifyIdToken(idToken, true);
            return new VerifiedIdentity(
                    token.getUid(),
                    token.getEmail(),
                    Boolean.TRUE.equals(token.isEmailVerified()));
        } catch (FirebaseAuthException exception) {
            throw new IllegalArgumentException("Token do Firebase inválido ou expirado.", exception);
        }
    }
}