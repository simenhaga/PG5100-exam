package no.kristiania.exam.unittests.controllers

import io.mockk.every
import io.mockk.mockk
import no.kristiania.exam.controller.ShelterController
import no.kristiania.exam.models.entities.AnimalEntity
import no.kristiania.exam.service.AnimalService
import no.kristiania.exam.service.RegisterAnimalDTO
import no.kristiania.exam.service.UserService
import org.json.JSONObject
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.testcontainers.shaded.com.fasterxml.jackson.databind.util.JSONPObject
import java.time.LocalDateTime

@WebMvcTest(ShelterController::class)
@AutoConfigureMockMvc(addFilters = false)
class ShelterControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun animalService() = mockk<AnimalService>()

        @Bean
        fun userService() = mockk<UserService>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var animalService: AnimalService

    @Test
    fun testRegisterEndpoint(){
        val checkOutTime = LocalDateTime.now()
        every { animalService.registerAnimal(RegisterAnimalDTO("Money", "Cat", 20, true, checkOutTime)) } answers {
            AnimalEntity(name = "Money", animalType = "Cat", animalAge = 20, vaccinated = true, checkOutTime = checkOutTime)
        }

        mockMvc.post("/api/shelter/register"){
            contentType = APPLICATION_JSON
            content = JSONObject(mapOf("name" to "Money", "animalType" to "Cat", "animalAge" to 20, "vaccinated" to true, "checkOutTime" to checkOutTime))
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
    }
}