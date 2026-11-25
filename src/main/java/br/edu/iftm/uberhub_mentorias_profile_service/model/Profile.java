package br.edu.iftm.uberhub_mentorias_profile_service.model;

import java.time.Instant;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "perfis")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    private String id;

    private String firebaseUid;
    private String nome;
    private String email;

    @Indexed(unique = true)
    private String mentorId;

    private String miniBio;

    private List<String> areas;

    private String schedulingLink;

    @Indexed
    private String status; // PENDING, APPROVED, REJECTED

    private Instant approvedAt;

    private String approvedBy;

    private Instant createdAt;

    private Instant updatedAt;
}