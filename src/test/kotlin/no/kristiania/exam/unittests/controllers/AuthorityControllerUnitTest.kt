package no.kristiania.exam.unittests.controllers

import io.mockk.every
import io.mockk.mockk
import no.kristiania.exam.controller.AuthorityController
import no.kristiania.exam.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@ExtendWith(SpringExtension::class)
@WebMvcTest(AuthorityController::class)
@AutoConfigureMockMvc(addFilters = false)
class AuthorityControllerUnitTest {

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
    fun testAuthorityAllEndpoint() {
        every { userService.getAuthorities() } answers {
            listOf("ADMIN", "USER")
        }

        mockMvc.get("/api/authority/all") {
        }
            .andExpect { status { isOk() } }
            .andExpect {
                jsonPath("$") { isArray() }
            }
    }
}