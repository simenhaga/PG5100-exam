package no.kristiania.exam.controller

import no.kristiania.exam.models.entities.AnimalEntity
import no.kristiania.exam.models.entities.UserEntity
import no.kristiania.exam.service.AnimalService
import no.kristiania.exam.service.RegisterAnimalDTO
import no.kristiania.exam.service.RegisterUserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/shelter")
class ShelterController(@Autowired private val animalService: AnimalService) {

    @GetMapping("/all")
    fun getAllAnimals(): ResponseEntity<List<AnimalEntity>> {
        return ResponseEntity.ok().body(animalService.getAllAnimals())
    }

    @PostMapping("/register")
    fun registerAnimal(@RequestBody newAnimal: RegisterAnimalDTO): ResponseEntity<AnimalEntity> {
        val animal = animalService.registerAnimal(newAnimal)
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/shelter/register").toUriString())
        return ResponseEntity.created(uri).body(animal)
    }
}