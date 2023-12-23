package com.ryofac.livbook.livbook.Models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
// TODO: Adcionar relacionamento entre hashtags e posts

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
public class Hashtag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;
    private Integer timesUsed;

    @CreationTimestamp
    private LocalDateTime firstUsedAt;

    @ManyToMany(mappedBy = "hashtags")
    @Builder.Default
    private List<Post> posts = new ArrayList<>();
   
}
