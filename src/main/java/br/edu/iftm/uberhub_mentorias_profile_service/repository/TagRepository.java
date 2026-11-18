package br.edu.iftm.uberhub_mentorias_profile_service.repository;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// @Arthur
@Repository
public interface TagRepository extends MongoRepository<Tag, String> {

    List<Tag> findByCategoryIgnoreCase(String category);
}