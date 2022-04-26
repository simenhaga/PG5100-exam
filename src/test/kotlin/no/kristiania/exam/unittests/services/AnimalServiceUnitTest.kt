package no.kristiania.exam.unittests.services

import io.mockk.every
import io.mockk.mockk
import no.kristiania.exam.models.entities.AnimalEntity
import no.kristiania.exam.models.entities.AuthorityEntity
import no.kristiania.exam.models.entities.UserEntity
import no.kristiania.exam.repos.AnimalRepo
import no.kristiania.exam.repos.AuthorityRepo
import no.kristiania.exam.repos.UserRepo
import no.kristiania.exam.service.AnimalService
import no.kristiania.exam.service.RegisterAnimalDTO
import no.kristiania.exam.service.RegisterUserDTO
import no.kristiania.exam.service.UserService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.LocalDateTime

class AnimalServiceUnitTest {

    private val animalRepo = mockk<AnimalRepo>()
    private val animalService = AnimalService(animalRepo)

    @Test
    fun getAnimals(){
        val animalOne = AnimalEntity(animalId = 1, name = "Random1", animalType = "monster", animalAge = 10, vaccinated = true)
        val animalTwo = AnimalEntity(animalId = 2, name = "Random2", animalType = "fish", animalAge = 10, vaccinated = true)

        every { animalRepo.findAll() } returns listOf(animalOne, animalTwo)

        val animalList = animalService.getAllAnimals()
        assert(animalList.size == 2)
        assert(animalList[0].animalId?.equals(1L) == true)
        assert(animalList[1].name == "Random2")
    }

    @Test
    fun registerAnimalTest(){
        every { animalRepo.save(any()) } answers {
            firstArg()
        }
        val createdAnimal = animalService.registerAnimal(RegisterAnimalDTO("pinky", "dog", 20, true, LocalDateTime.now()))
        assert(createdAnimal.name == "pinky")
    }

}