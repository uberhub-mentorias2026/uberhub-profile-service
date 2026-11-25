package br.edu.iftm.uberhub_mentorias_profile_service.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Tag;
import br.edu.iftm.uberhub_mentorias_profile_service.repository.TagRepository;

// @Arthur
@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> listarTodas(String category) {
        if (category != null && !category.isBlank()) {
            return tagRepository.findByCategoryIgnoreCase(category.trim());
        }
        return tagRepository.findAll();
    }

    public Tag criar(Tag request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("tag_name_required");
        }
        if (tagRepository.existsByNameIgnoreCase(request.getName().trim())) {
            throw new IllegalStateException("tag_already_exists");
        }

        request.setName(request.getName().trim());
        if (request.getCategory() != null) {
            request.setCategory(request.getCategory().trim());
        }
        return tagRepository.save(request);
    }

    public Tag atualizar(String id, Tag request) {
        Tag existente = tagRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("tag_not_found"));

        if (request.getName() != null && !request.getName().isBlank()) {
            existente.setName(request.getName().trim());
        }
        if (request.getCategory() != null && !request.getCategory().isBlank()) {
            existente.setCategory(request.getCategory().trim());
        }
        if (request.getDescription() != null) {
            existente.setDescription(request.getDescription().trim());
        }

        return tagRepository.save(existente);
    }

    public void deletar(String id) {
        if (!tagRepository.existsById(id)) {
            throw new NoSuchElementException("tag_not_found");
        }
        tagRepository.deleteById(id);
    }
}