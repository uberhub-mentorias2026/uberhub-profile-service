package br.edu.iftm.uberhub_mentorias_profile_service.service;

import java.time.Instant;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Profile;
import br.edu.iftm.uberhub_mentorias_profile_service.repository.ProfileRepository;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    public Profile salvarPerfil(Profile profile) {
        return profileRepository.save(profile);
    }

    // @Arthur - Lista todos os perfis
    public List<Profile> listarPerfis() {
        return profileRepository.findAll();
    }

    // @Arthur - POST /profiles/mentor (cria perfil iniciando como PENDING)
    public Profile criarPerfilMentor(Profile request) {
        if (request.getMentorId() != null && profileRepository.existsByMentorId(request.getMentorId())) {
            throw new IllegalStateException("profile_already_exists");
        }

        Instant agora = Instant.now();
        request.setStatus("PENDING");
        request.setCreatedAt(agora);
        request.setUpdatedAt(agora);
        return profileRepository.save(request);
    }

    // @Arthur - PUT /profiles/mentor (atualiza campos enviados sem mudar o status)
    public Profile atualizarPerfilMentor(String mentorId, Profile request) {
        Profile existente = buscarPorMentorId(mentorId);

        if (request.getMiniBio() != null && !request.getMiniBio().isBlank()) {
            existente.setMiniBio(request.getMiniBio().trim());
        }
        if (request.getAreas() != null && !request.getAreas().isEmpty()) {
            existente.setAreas(request.getAreas());
        }
        if (request.getSchedulingLink() != null && !request.getSchedulingLink().isBlank()) {
            existente.setSchedulingLink(request.getSchedulingLink().trim());
        }
        if (request.getNome() != null && !request.getNome().isBlank()) {
            existente.setNome(request.getNome().trim());
        }

        existente.setUpdatedAt(Instant.now());
        return profileRepository.save(existente);
    }

    // @Arthur - GET /profiles/mentor/{mentorId}
    public Profile buscarPorMentorId(String mentorId) {
        return profileRepository.findByMentorId(mentorId)
                .orElseThrow(() -> new NoSuchElementException("profile_not_found"));
    }

    // @Arthur - GET /profiles/mentors?status=&area=&limit=20&offset=0
    public List<Profile> listarMentores(String status, String area, int limit, int offset) {
        List<Profile> perfis = (status != null && !status.isBlank())
                ? profileRepository.findByStatus(status.trim().toUpperCase(Locale.ROOT))
                : profileRepository.findAll();

        if (area != null && !area.isBlank()) {
            String areaFiltro = area.trim().toLowerCase(Locale.ROOT);
            perfis = perfis.stream()
                    .filter(p -> p.getAreas() != null && p.getAreas().stream()
                            .anyMatch(a -> a != null && a.trim().toLowerCase(Locale.ROOT).equals(areaFiltro)))
                    .toList();
        }

        int safeOffset = Math.max(0, offset);
        int safeLimit = limit <= 0 ? 20 : Math.min(limit, 100);

        return perfis.stream()
                .skip(safeOffset)
                .limit(safeLimit)
                .toList();
    }

    // @Arthur - GET /profiles/mentors/pending
    public List<Profile> listarMentoresPendentes() {
        return profileRepository.findByStatus("PENDING");
    }

    // @Arthur - PUT /profiles/mentor/{mentorId}/status
    public Profile atualizarStatusPerfil(String mentorId, String novoStatus, String approvedBy) {
        if (novoStatus == null
                || (!novoStatus.equalsIgnoreCase("APPROVED") && !novoStatus.equalsIgnoreCase("REJECTED"))) {
            throw new IllegalArgumentException("invalid_status");
        }

        Profile perfil = buscarPorMentorId(mentorId);
        String statusNormalizado = novoStatus.trim().toUpperCase(Locale.ROOT);
        Instant agora = Instant.now();

        perfil.setStatus(statusNormalizado);
        perfil.setUpdatedAt(agora);

        if ("APPROVED".equals(statusNormalizado)) {
            perfil.setApprovedAt(agora);
            perfil.setApprovedBy(approvedBy != null && !approvedBy.isBlank() ? approvedBy : "admin");
        }

        return profileRepository.save(perfil);
    }
}