package com.ryofac.livbook.livbook.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Para facilitar o trabalho de colocar coisinha por coisinha: Lombok faz pra mim
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
// TODO: Sobrescrever o método equals da classe para fazer com que ele seja igual pelo nome, email e senha
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 100, unique = true, nullable = false)
    @NotBlank
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @NotBlank
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

    @Column(name= "photoUrl", nullable = true)
    private String profilePhotoUrl;
    
    @CreationTimestamp
    private LocalDateTime createdOn;

    @OneToMany(mappedBy = "owner")// Forma de dizer pra persistência que esse relacionamento será 1 - n
    @Builder.Default // O default não será nulo, mas uma nova ArrayList vazia
    private List<Post> posts = new ArrayList<>();
    
    
}
