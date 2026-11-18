package br.edu.iftm.uberhub_mentorias_profile_service.controller;

import br.edu.iftm.uberhub_mentorias_profile_service.model.MentorProfile;
import br.edu.iftm.uberhub_mentorias_profile_service.model.Tag;
import br.edu.iftm.uberhub_mentorias_profile_service.repository.MentorProfileRepository;
import br.edu.iftm.uberhub_mentorias_profile_service.repository.TagRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

// @Arthur
@RestController
public class ProfileController {

    private final TagRepository tagRepository;
    private final MentorProfileRepository profileRepository;

    // Injeção de dependência
    public ProfileController(TagRepository tagRepository, MentorProfileRepository profileRepository) {
        this.tagRepository = tagRepository;
        this.profileRepository = profileRepository;
    }

    // Verificação de saúde do serviço
    @GetMapping({ "/profiles/health", "/api/v1/profiles/health" })
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "uberhub-profile-service"));
    }

    // Listar todas as tags do MongoDB
    @GetMapping({ "/tags", "/api/v1/tags" })
    public ResponseEntity<List<Tag>> listTags() {
        return ResponseEntity.ok(tagRepository.findAll());
    }

    // Criar uma nova tag no MongoDB
    @PostMapping({ "/tags", "/api/v1/tags" })
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagRepository.save(tag));
    }

    // Criar um perfil de mentor no MongoDB (nasce com status PENDING)
    @PostMapping({ "/profiles/mentor", "/api/v1/profiles/mentor" })
    public ResponseEntity<MentorProfile> createProfile(@RequestBody MentorProfile profile) {
        profile.setStatus("PENDING");
        profile.setCreatedAt(Instant.now());
        profile.setUpdatedAt(Instant.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(profileRepository.save(profile));
    }

    // Listar perfis de mentores do MongoDB
    @GetMapping({ "/profiles/mentors", "/api/v1/profiles/mentors" })
    public ResponseEntity<List<MentorProfile>> listMentors(@RequestParam(required = false) String status) {
        if (status != null && !status.isBlank()) {
            return ResponseEntity.ok(profileRepository.findByStatus(status));
        }
        return ResponseEntity.ok(profileRepository.findAll());
    }
}