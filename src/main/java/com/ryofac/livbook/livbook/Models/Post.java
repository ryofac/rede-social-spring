package com.ryofac.livbook.livbook.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
// Entidade: Sinalizando que essa classe será meu modelo do banco de dados
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Convenção é colocar como Long
    private String photoAttachementURL;

    @NotBlank
    private String text;
    
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime editedAt;

    @ManyToOne 
    @JoinColumn(name = "user-id", updatable = false, unique = false)
    // Devo dizer que esse é o tipo específico de coluna que referencia dados de outra tabela
    private Profile owner;
    // Isso é uma relação N pra N, descobrir como lidar com isso no Banco
}
