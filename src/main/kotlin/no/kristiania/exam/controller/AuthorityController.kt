package no.kristiania.exam.controller

import no.kristiania.exam.models.entities.AuthorityEntity

import no.kristiania.exam.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/authority")
class AuthorityController(@Autowired private val userService: UserService) {

    @PostMapping("/create")
    fun registerAuthority(@RequestBody authority: AuthorityEntity): ResponseEntity<AuthorityEntity> {
        val authorityEntity = userService.createAuthority(authority)
        val uri = URI.create(
            ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/authority/create").toUriString()
        )
        return ResponseEntity.created(uri).body(authorityEntity)
    }

    @GetMapping("/all")
    fun getAuthorities(): ResponseEntity<List<String?>> {
        return ResponseEntity.ok().body(userService.getAuthorities())
    }

    @PostMapping("/addToUser")
    fun addAuthorityToUser(@RequestBody authorityToUser: AuthorityToUser): ResponseEntity<Any> {
        userService.grantAuthorityToUser(authorityToUser.email, authorityToUser.authority)
        return ResponseEntity.ok().build()
    }

}

data class AuthorityToUser(val email: String?, val authority: String?)
