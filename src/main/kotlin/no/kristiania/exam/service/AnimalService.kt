package no.kristiania.exam.service

import no.kristiania.exam.models.entities.AnimalEntity
import no.kristiania.exam.repos.AnimalRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AnimalService(
    @Autowired private val animalRepo: AnimalRepo
){

    fun getAnimalByName(name: String): AnimalEntity {
        name.let {
            return animalRepo.findByName(it)
        }
    }

    fun registerAnimal(animal: RegisterAnimalDTO): AnimalEntity {
        val newAnimal = AnimalEntity(
            name = animal.name,
            animalType = animal.animalType,
            animalAge = animal.animalAge,
            vaccinated = animal.vaccinated)

        return animalRepo.save(newAnimal)
    }

    fun getAllAnimals(): List<AnimalEntity>{
        return animalRepo.findAll()
    }

    fun updateAnimal(animal: RegisterAnimalDTO) {
        val oldAnimal: AnimalEntity = animalRepo.findByName(animal.name)
        animalRepo.save(AnimalEntity(animalId = oldAnimal.animalId, name = animal.name, animalType = animal.animalType, animalAge = animal.animalAge, vaccinated = animal.vaccinated))
    }

    fun updateAnimalID(animal: UpdateAnimalDTO) {
        val oldAnimal = animalRepo.findByAnimalId(animal.animalId)
        animalRepo.save(AnimalEntity(animalId = oldAnimal.animalId, name = animal.name, animalType = animal.animalType, animalAge = animal.animalAge, vaccinated = animal.vaccinated))
    }

}

data class RegisterAnimalDTO(
    val name: String,
    val animalType: String,
    val animalAge: Int,
    val vaccinated: Boolean
    )

data class UpdateAnimalDTO(
    val animalId: Long,
    val name: String,
    val animalType: String,
    val animalAge: Int,
    val vaccinated: Boolean
)