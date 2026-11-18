package br.edu.iftm.uberhub_mentorias_profile_service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

// @Arthur
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tags")
public class Tag {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    private String category;

    private String description;
}