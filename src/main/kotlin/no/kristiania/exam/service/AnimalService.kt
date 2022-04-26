package no.kristiania.exam.service

import no.kristiania.exam.models.entities.AnimalEntity
import no.kristiania.exam.repos.AnimalRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AnimalService(
    @Autowired private val animalRepo: AnimalRepo
){

    fun getAnimalById(animalId: Long): AnimalEntity?{
        return animalRepo.getById(animalId)
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


    fun updateAnimalById(animal: RegisterAnimalDTO, animalId: Long): AnimalEntity? {
        animalRepo.findByAnimalId(animalId) ?.let {
            return animalRepo.save(AnimalEntity(animalId = animalId, name = animal.name, animalType = animal.animalType, animalAge = animal.animalAge, vaccinated = animal.vaccinated, checkOutTime = animal.checkOutTime))
        }
        return null;
    }

    fun deleteAnimalById(animalId: Long) {
        animalRepo.findByAnimalId(animalId) ?.let {
            animalRepo.delete(it)
        }
    }

}

data class RegisterAnimalDTO(
    val name: String,
    val animalType: String,
    val animalAge: Int,
    val vaccinated: Boolean,
    val checkOutTime: LocalDateTime?
    )