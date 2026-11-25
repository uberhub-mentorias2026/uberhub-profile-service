package br.edu.iftm.uberhub_mentorias_profile_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Profile;

@Repository
public interface ProfileRepository extends MongoRepository<Profile, String> {

    Optional<Profile> findByFirebaseUid(String firebaseUid);

    // @Arthur - Consultas de perfil de mentor
    Optional<Profile> findByMentorId(String mentorId);

    boolean existsByMentorId(String mentorId);

    List<Profile> findByStatus(String status);
}