package no.kristiania.exam.unittests.controllers


import io.mockk.every
import io.mockk.mockk
import no.kristiania.exam.controller.UserController
import no.kristiania.exam.models.entities.UserEntity
import no.kristiania.exam.service.RegisterUserDTO
import no.kristiania.exam.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@ExtendWith(SpringExtension::class)
@WebMvcTest(UserController::class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerUnitTest {

    @TestConfiguration
    class ControllerTestConfig {
        @Bean
        fun userService() = mockk<UserService>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun testRegisterEndpoint(){
        every { userService.registerUser(RegisterUserDTO("jim@bob.com", "pirate")) } answers {
            val user = UserEntity(userId = 1, email = "jim@bob.com", password = BCryptPasswordEncoder().encode("pirate"))
            user
        }

        mockMvc.post("/api/user/register"){
            contentType = APPLICATION_JSON
            content = "{\"email\":\"jim@bob.com\", \"password\":\"pirate\"}"
        }
            .andExpect { status { isCreated() } }
            .andExpect { content { contentType(APPLICATION_JSON) } }
    }
}