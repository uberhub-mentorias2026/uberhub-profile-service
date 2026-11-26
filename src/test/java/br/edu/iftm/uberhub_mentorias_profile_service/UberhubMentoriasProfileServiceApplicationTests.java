package br.edu.iftm.uberhub_mentorias_profile_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import br.edu.iftm.uberhub_mentorias_profile_service.port.FirebaseIdentityVerifier;
import br.edu.iftm.uberhub_mentorias_profile_service.repository.ProfileRepository;
import br.edu.iftm.uberhub_mentorias_profile_service.repository.TagRepository;

@SpringBootTest(properties = "profile.seed-tags=false")
class UberhubMentoriasProfileServiceApplicationTests {

    @MockitoBean
    FirebaseIdentityVerifier firebaseIdentityVerifier;

    @MockitoBean
    ProfileRepository profileRepository;

    @MockitoBean
    TagRepository tagRepository;

    @Test
    void contextLoads() {
    }
}
