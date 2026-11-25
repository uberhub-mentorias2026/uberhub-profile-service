package br.edu.iftm.uberhub_mentorias_profile_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Arthur
@Document(collection = "tags")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @Indexed
    private String category;

    private String description;
}