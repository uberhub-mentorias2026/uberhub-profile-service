package br.edu.iftm.uberhub_mentorias_profile_service.service;

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
}
