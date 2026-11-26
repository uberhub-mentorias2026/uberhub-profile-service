package br.edu.iftm.uberhub_mentorias_profile_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.edu.iftm.uberhub_mentorias_profile_service.exception.GlobalExceptionHandler;
import br.edu.iftm.uberhub_mentorias_profile_service.model.Profile;
import br.edu.iftm.uberhub_mentorias_profile_service.model.Tag;
import br.edu.iftm.uberhub_mentorias_profile_service.service.ProfileService;
import br.edu.iftm.uberhub_mentorias_profile_service.service.TagService;

// @Arthur - Testes HTTP dos endpoints do ProfileController e TagController
class ProfileControllerTest {

    private ProfileService profileService;
    private TagService tagService;
    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        profileService = mock(ProfileService.class);
        tagService = mock(TagService.class);
        mvc = MockMvcBuilders.standaloneSetup(
                        new ProfileController(profileService),
                        new TagController(tagService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void healthRetornaStatusUp() throws Exception {
        mvc.perform(get("/api/v1/profiles/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void criarPerfilMentorDuplicadoRetorna400ComCodigoEstavel() throws Exception {
        when(profileService.criarPerfilMentor(any(Profile.class)))
                .thenThrow(new IllegalStateException("profile_already_exists"));

        mvc.perform(post("/api/v1/profiles/mentor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mentorId\":\"m1\",\"miniBio\":\"Bio\",\"areas\":[\"Java\"]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("profile_already_exists"));
    }

    @Test
    void listarTagsRetorna200() throws Exception {
        when(tagService.listarTodas(null)).thenReturn(List.of(
                Tag.builder().id("t1").name("Java").category("Desenvolvimento Backend").build()));

        mvc.perform(get("/api/v1/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Java"));
    }
}
