package br.edu.iftm.uberhub_mentorias_profile_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Profile;
import br.edu.iftm.uberhub_mentorias_profile_service.service.ProfileService;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Profile> criarPerfil(@RequestBody Profile profile) {
        Profile novoPerfil = profileService.salvarPerfil(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPerfil);
    }

    // @Arthur - Lista os perfis cadastrados no MongoDB
    @GetMapping
    public ResponseEntity<List<Profile>> listarPerfis() {
        return ResponseEntity.ok(profileService.listarPerfis());
    }

    // @Arthur - Verificação de saúde do serviço
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "uberhub-profile-service"
        ));
    }
}
