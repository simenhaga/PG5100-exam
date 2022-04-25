package no.kristiania.exam.controller

import no.kristiania.exam.models.entities.UserEntity
import no.kristiania.exam.service.RegisterUserDTO
import no.kristiania.exam.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/user")
class UserController(@Autowired private val userService: UserService) {

    @GetMapping("/all")
    fun getUsers(): ResponseEntity<List<UserEntity>> {
        return ResponseEntity.ok().body(userService.getUsers())
    }

    @PostMapping("/register")
    fun registerUser(@RequestBody newUser: RegisterUserDTO): ResponseEntity<UserEntity> {
        val user = userService.registerUser(newUser)
        val uri =
            URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/register").toUriString())
        return ResponseEntity.created(uri).body(user)
    }

}


