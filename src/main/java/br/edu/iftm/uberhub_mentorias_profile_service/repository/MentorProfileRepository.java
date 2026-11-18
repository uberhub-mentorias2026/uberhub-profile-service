package br.edu.iftm.uberhub_mentorias_profile_service.repository;

import br.edu.iftm.uberhub_mentorias_profile_service.model.MentorProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// @Arthur
@Repository
public interface MentorProfileRepository extends MongoRepository<MentorProfile, String> {

    Optional<MentorProfile> findByMentorId(String mentorId);

    List<MentorProfile> findByStatus(String status);
}