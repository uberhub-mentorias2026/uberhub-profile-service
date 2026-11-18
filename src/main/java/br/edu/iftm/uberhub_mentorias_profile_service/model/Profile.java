package br.edu.iftm.uberhub_mentorias_profile_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "perfis")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {

    @Id
    private String id;
    private String firebaseUid;
    private String nome;
    private String email;
}