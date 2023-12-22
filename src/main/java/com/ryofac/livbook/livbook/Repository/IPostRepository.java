package com.ryofac.livbook.livbook.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ryofac.livbook.livbook.Models.Post;

public interface IPostRepository extends JpaRepository<Post, Long> {

    // Usando JPQL: Uma forma de misturar java + SQL
    // @Param -> indica que é um parametro
    // Inicia-se com a annotation @Query em cima do método

    @Query(value = "SELECT p FROM Post p WHERE p.owner.id = :id")
    // Mistura de SQL com orientação a objetos
    // Query normal (Que pode ser obtida pelo @Query(nativeQuery = true))
    // SELECT * FROM POST P WHERE P.OWNERID = :id
    //  -> ele acessa o ownerId da tabela/modelo Profile e seleciona os "objetos"
    // Buscando os posts baseados em profile id específico
    List<Post> findbyProfileId(@Param("id") Long id);

    List<Post> findByOwnerId(Long id);
    
}
