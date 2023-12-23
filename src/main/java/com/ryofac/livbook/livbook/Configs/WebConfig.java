package com.ryofac.livbook.livbook.Configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// Devo importar esse WebMVCConfigurar para configurar as conexões de CORS
// Segundo a documentação do Spring : essa classe é uma interface que fornece métodos de callback para personalizar a configuração do Spring MVC
@Configuration // Indicando para o Spring que é um Componente de Configuração
@EnableWebMvc // Habilitando o Spring MVC
public class WebConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Indicando que qualquer origem pode acessar a API
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Indicando os métodos que podem ser utilizados
    }


    
}
