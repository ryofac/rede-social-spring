package com.ryofac.livbook.livbook.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Length;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
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
// Entidade: Sinalizando que essa classe será meu modelo do banco de dados
public class Post {

    // Criação de interfaces para o uso da validação do spring
    public interface CreatePost {
    
    }

    public interface UpdatePost {
    
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Convenção é colocar como Long
    private String photoAttachementURL;

    @NotEmpty(groups = {CreatePost.class, UpdatePost.class})
    @NotNull(groups = {CreatePost.class, UpdatePost.class})
    @org.hibernate.validator.constraints.Length(max=100)
    private String text;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime editedAt;

    @ManyToOne
    @NotEmpty(groups = CreatePost.class)
    @NotNull(groups = CreatePost.class)
    @JoinColumn(name = "ownerId", updatable = false, unique = false, nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    // Devo dizer que esse é o tipo específico de coluna que referencia dados de outra tabela

    @JsonIgnore
    private Profile owner;

    @PreRemove
    // Removendo as hashtags
    private void removingHashTag(){
       for(Hashtag hashtag: hashtags){
        hashtag.getPosts().remove(this);
       }
       hashtags.clear();
    }

    // Isso é uma relação N pra N, precisa fazer uma "tabela de junção" para lidar com isso
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch = FetchType.EAGER)
     @JoinTable (
        name = "post_hashtag", 
        joinColumns = @JoinColumn(name = "post_id"), 
        inverseJoinColumns = @JoinColumn(name = "hashtag_id", nullable = true)
    )
    @Builder.Default
    private List<Hashtag> hashtags = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

}
