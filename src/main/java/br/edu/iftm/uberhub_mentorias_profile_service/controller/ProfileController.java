package br.edu.iftm.uberhub_mentorias_profile_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Profile;
import br.edu.iftm.uberhub_mentorias_profile_service.service.ProfileService;

@RestController
@RequestMapping({ "/profiles", "/api/v1/profiles" })
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

    // @Arthur - POST /profiles/mentor (Mentorado cria perfil de mentor com status
    // PENDING)
    @PostMapping("/mentor")
    public ResponseEntity<Profile> criarPerfilMentor(@RequestBody Profile profile) {
        Profile criado = profileService.criarPerfilMentor(profile);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    // @Arthur - PUT /profiles/mentor (Mentor atualiza o próprio perfil)
    @PutMapping("/mentor")
    public ResponseEntity<Profile> atualizarPerfilMentor(
            @RequestParam(required = false) String mentorId,
            @RequestBody Profile profile) {
        String idAlvo = (mentorId != null && !mentorId.isBlank()) ? mentorId : profile.getMentorId();
        return ResponseEntity.ok(profileService.atualizarPerfilMentor(idAlvo, profile));
    }

    // @Arthur - GET /profiles/mentor/{mentorId} (Detalhes do perfil de um mentor)
    @GetMapping("/mentor/{mentorId}")
    public ResponseEntity<Profile> buscarPorMentorId(@PathVariable String mentorId) {
        return ResponseEntity.ok(profileService.buscarPorMentorId(mentorId));
    }

    // @Arthur - GET /profiles/mentors?status=&area=&limit=20&offset=0 (Lista
    // mentores com filtros)
    @GetMapping("/mentors")
    public ResponseEntity<List<Profile>> listarMentores(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String area,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "0") int offset) {
        return ResponseEntity.ok(profileService.listarMentores(status, area, limit, offset));
    }

    // @Arthur - GET /profiles/mentors/pending (Admin lista perfis com status
    // PENDING)
    @GetMapping("/mentors/pending")
    public ResponseEntity<List<Profile>> listarMentoresPendentes() {
        return ResponseEntity.ok(profileService.listarMentoresPendentes());
    }

    // @Arthur - PUT /profiles/mentor/{mentorId}/status (Admin aprova ou reprova
    // mentor)
    @PutMapping("/mentor/{mentorId}/status")
    public ResponseEntity<Profile> atualizarStatusPerfil(
            @PathVariable String mentorId,
            @RequestBody Map<String, String> body) {
        String status = body.get("status");
        String approvedBy = body.get("approvedBy");
        return ResponseEntity.ok(profileService.atualizarStatusPerfil(mentorId, status, approvedBy));
    }

    // @Arthur - Verificação de saúde do serviço
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "UP",
                "service", "uberhub-profile-service"));
    }
}