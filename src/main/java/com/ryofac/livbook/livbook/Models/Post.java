package com.ryofac.livbook.livbook.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Para facilitar o trabalho de colocar coisinha por coisinha: Lombok faz pra mim
@Data
@NoArgsConstructor
@AllArgsConstructor

// Entidade: Sinalizando que essa classe será meu modelo do banco de dados
public class Post {
    private Long id; // Convenção é colocar como Long
    private LocalDateTime createdTime;

    
}
