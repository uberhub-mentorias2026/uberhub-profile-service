package br.edu.iftm.uberhub_mentorias_profile_service.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.uberhub_mentorias_profile_service.model.Tag;
import br.edu.iftm.uberhub_mentorias_profile_service.service.TagService;

// @Arthur
@RestController
@RequestMapping({ "/tags", "/api/v1/tags" })
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    // GET /tags (Público - lista todas as tags de especialização)
    @GetMapping
    public ResponseEntity<List<Tag>> listarTags(@RequestParam(required = false) String category) {
        return ResponseEntity.ok(tagService.listarTodas(category));
    }

    // POST /tags (Admin - cria uma nova tag)
    @PostMapping
    public ResponseEntity<Tag> criarTag(@RequestBody Tag tag) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.criar(tag));
    }

    // PUT /tags/{id} (Admin - atualiza uma tag)
    @PutMapping("/{id}")
    public ResponseEntity<Tag> atualizarTag(@PathVariable String id, @RequestBody Tag tag) {
        return ResponseEntity.ok(tagService.atualizar(id, tag));
    }

    // DELETE /tags/{id} (Admin - remove uma tag)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarTag(@PathVariable String id) {
        tagService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}