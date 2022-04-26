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


    fun updateAnimalById(animal: RegisterAnimalDTO, animalId: Long) {
        animalRepo.findByAnimalId(animalId)
        animalRepo.save(AnimalEntity(animalId = animalId, name = animal.name, animalType = animal.animalType, animalAge = animal.animalAge, vaccinated = animal.vaccinated))
    }

    fun deleteAnimal(animal: UpdateAnimalDTO) {
        val animalToDelete = animalRepo.findByAnimalId(animal.animalId)
        animalRepo.delete(animalToDelete)
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