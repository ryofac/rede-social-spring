package com.ryofac.livbook.livbook.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class Profile {

    // Criação de interfaces para o uso da validação do spring 
    // Criação de grupos
    // >>>
    public interface CreateProfile {
    }

    public interface UpdateProfile { 
    }
    /// <<<

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", length = 100, unique = true, nullable = false)
    @NotNull(groups = CreateProfile.class)
    @NotEmpty(groups = CreateProfile.class)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @NotEmpty(groups = CreateProfile.class)
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull(groups = {CreateProfile.class, UpdateProfile.class })
    @NotEmpty(groups = {CreateProfile.class, UpdateProfile.class} )
    private String password;

    @Column(name= "photoUrl", nullable = true)
    @NotNull (groups = {CreateProfile.class, UpdateProfile.class} )
    @NotEmpty (groups = {CreateProfile.class, UpdateProfile.class} )
    private String profilePhotoUrl;
    
    @CreationTimestamp
    private LocalDateTime createdOn;

    @OneToMany(mappedBy = "owner")// Forma de dizer pra persistência que esse relacionamento será 1 - n
    @Builder.Default // O default não será nulo, mas uma nova ArrayList vazia
    private List<Post> posts = new ArrayList<>();

    @PreRemove
    private void removePosts() {
        for (Post post : posts) {
            post.setOwner(null);
        }
    }
    
}
