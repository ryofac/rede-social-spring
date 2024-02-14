package com.ryofac.livbook.livbook.Models;


import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    public interface CreateComment {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(groups = {CreateComment.class})
    @NotNull
    @Length(max = 100)
    private String content;

    @ManyToOne
    @NotEmpty(groups = {CreateComment.class})
    @NotNull
    private Profile owner;

    @ManyToOne
    private Post post;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
