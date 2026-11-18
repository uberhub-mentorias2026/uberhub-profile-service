package br.edu.iftm.uberhub_mentorias_profile_service.port;

import br.edu.iftm.uberhub_mentorias_profile_service.model.VerifiedIdentity;

public interface FirebaseIdentityVerifier {

    VerifiedIdentity verify(String idToken);

}
