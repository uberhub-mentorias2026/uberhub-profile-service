package br.edu.iftm.uberhub_mentorias_profile_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Tag;

// @Arthur
@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

    Optional<Tag> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Tag> findByCategoryIgnoreCase(String category);
}