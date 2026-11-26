package br.edu.iftm.uberhub_mentorias_profile_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Profile;
import br.edu.iftm.uberhub_mentorias_profile_service.repository.ProfileRepository;

// @Arthur - Testes unitários das regras de negócio do ProfileService
class ProfileServiceTest {

    private ProfileRepository profileRepository;
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        profileRepository = mock(ProfileRepository.class);
        profileService = new ProfileService(profileRepository);
    }

    @Test
    void criarPerfilMentorIniciaComStatusPending() {
        Profile novo = Profile.builder()
                .mentorId("mentor-1")
                .miniBio("Desenvolvedor Java")
                .areas(List.of("Java", "Spring Boot"))
                .schedulingLink("https://calendly.com/mentor1")
                .build();

        when(profileRepository.existsByMentorId("mentor-1")).thenReturn(false);
        when(profileRepository.save(any(Profile.class))).thenAnswer(inv -> inv.getArgument(0));

        Profile salvo = profileService.criarPerfilMentor(novo);

        assertEquals("PENDING", salvo.getStatus());
        assertNotNull(salvo.getCreatedAt());
        assertNotNull(salvo.getUpdatedAt());
    }

    @Test
    void criarPerfilMentorDuplicadoLancaExcecao() {
        Profile duplicado = Profile.builder().mentorId("mentor-1").build();
        when(profileRepository.existsByMentorId("mentor-1")).thenReturn(true);

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> profileService.criarPerfilMentor(duplicado));

        assertEquals("profile_already_exists", ex.getMessage());
    }

    @Test
    void aprovarPerfilDefineStatusApprovedEDataDeAprovacao() {
        Profile pendente = Profile.builder()
                .mentorId("mentor-1")
                .status("PENDING")
                .build();

        when(profileRepository.findByMentorId("mentor-1")).thenReturn(Optional.of(pendente));
        when(profileRepository.save(any(Profile.class))).thenAnswer(inv -> inv.getArgument(0));

        Profile aprovado = profileService.atualizarStatusPerfil("mentor-1", "APPROVED", "admin@iftm.edu.br");

        assertEquals("APPROVED", aprovado.getStatus());
        assertEquals("admin@iftm.edu.br", aprovado.getApprovedBy());
        assertNotNull(aprovado.getApprovedAt());
    }
}
