package no.kristiania.exam.unittests.services

import io.mockk.every
import io.mockk.mockk
import no.kristiania.exam.models.entities.AnimalEntity
import no.kristiania.exam.repos.AnimalRepo
import no.kristiania.exam.service.AnimalService
import no.kristiania.exam.service.RegisterAnimalDTO
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class AnimalServiceUnitTest {

    private val animalRepo = mockk<AnimalRepo>()
    private val animalService = AnimalService(animalRepo)

    @Test
    fun getAnimals(){
        val animalOne = AnimalEntity(animalId = 1, name = "Random1", animalType = "monster", animalAge = 10, vaccinated = true)
        val animalTwo = AnimalEntity(animalId = 2, name = "Random2", animalType = "fish", animalAge = 10, vaccinated = true)

        every { animalRepo.findAll() } returns listOf(animalOne, animalTwo) as MutableList<AnimalEntity>

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
        val createdAnimal = animalService.registerAnimal(RegisterAnimalDTO(name = "Random2", animalType = "fish", animalAge = 10, vaccinated = true, LocalDateTime.now()))
        assert(createdAnimal.name == "Random2")
    }

    @Test
    fun updateAnimal() {
        val animalOne = AnimalEntity(animalId = 1, name = "Random1", animalType = "monster", animalAge = 10, vaccinated = true)
        val updatedAnimal = RegisterAnimalDTO(name = "Random2", animalType = "fish", animalAge = 10, vaccinated = true, LocalDateTime.now())

        animalService.updateAnimalById(updatedAnimal, animalOne.animalId!!)

        every { animalRepo.findAll() } returns listOf(animalOne) as MutableList<AnimalEntity>

        val animalList = animalService.getAllAnimals()
        assert(animalList.size == 1)
        assert(animalList[0].animalType == "fish")
        assert(animalList[0].name == "Random2")
    }

    @Test
    fun deleteAnimal() {
        val animalOne = AnimalEntity(animalId = 1, name = "Random1", animalType = "monster", animalAge = 10, vaccinated = true)
        val animalTwo = AnimalEntity(animalId = 2, name = "Random2", animalType = "fish", animalAge = 20, vaccinated = false)
        val animalThree = AnimalEntity(animalId = 3, name = "Random3", animalType = "Dog", animalAge = 30, vaccinated = true)

        animalService.deleteAnimalById(animalTwo.animalId!!)

        every { animalRepo.findAll() } returns listOf(animalOne, animalTwo, animalThree) as MutableList<AnimalEntity>

        val animalList = animalService.getAllAnimals()
        assert(animalList.size == 2)
    }
}