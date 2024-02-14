package com.ryofac.livbook.livbook.Controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ryofac.livbook.livbook.Models.Profile;
import com.ryofac.livbook.livbook.Models.Profile.CreateProfile;
import com.ryofac.livbook.livbook.Models.Profile.UpdateProfile;
import com.ryofac.livbook.livbook.Services.ProfileService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/profile") // As rotas que começam com /profile vão ser tratadas por esse controller
@Validated // Indica que será efetuada uma validação de dados
// TODO : Adcionar e tratar mais tipos de Exceções:
public class ProfileController {
    private ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }
    
    // O get mapping deixa claro que é uma requisição do tipo GET
    // O id vem entre chaves porque ele será passado como parâmetro para a função
    // Também deve-se colocar como o PATHVARIABLE para indicar que ele é um parâmetro
    @GetMapping("/{id}")
    public ResponseEntity<Profile> findById(@PathVariable Long id){
        Profile found;
        found = profileService.findbyProfileId(id);
        return ResponseEntity.ok(found);
       
        // Significa que retorna um 200, um OK
    }

    @PostMapping
    @Validated(CreateProfile.class) // Dizendo que está validado segundo o grupo CreateProfile
    // O Valid servirá para validar o Body que estará vindo, representando um objeto de Profile
    public ResponseEntity<Profile> createProfile(@Valid @RequestBody Profile profile) {
        profileService.createProfile(profile);
        URI createdLoc = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}").buildAndExpand(profile.getId()).toUri();
        // O created é utilizado para descrever uma criação de recurso, direcionada a uma URI
        return ResponseEntity.created(createdLoc).build();
            
    }

    @PutMapping("/{id}")
    @Validated(UpdateProfile.class)
    public ResponseEntity<Profile> updateProfile(@Valid @PathVariable Long id, @RequestBody Profile toBeUpdated) {
        toBeUpdated.setId(id);
        profileService.updateProfile(toBeUpdated);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        profileService.removeProfile(id);
        return ResponseEntity.noContent().build();
    }



    
}
