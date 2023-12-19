package com.ryofac.livbook.livbook.Models;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Para facilitar o trabalho de colocar coisinha por coisinha: Lombok faz pra mim
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String profilePhotoUrl;
    
    @CreationTimestamp
    private LocalDateTime createdOn;
    
}
