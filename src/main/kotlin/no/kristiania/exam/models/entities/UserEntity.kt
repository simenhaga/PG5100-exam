package no.kristiania.exam.models.entities

import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_user_id_seq")
    @SequenceGenerator(
        name = "users_user_id_seq",
        allocationSize = 1
    )
    @Column(name = "user_id", nullable = false)
    val userId: Long? = null,

    @Column(name = "user_email")
    val email: String?,

    @Column(name = "user_password")
    val password: String? = null,

    @Column(name = "user_created")
    val created: LocalDateTime = LocalDateTime.now()
) {
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_authorities",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "authority_id")]
    )
    val authorities: MutableList<AuthorityEntity> = mutableListOf()


    override fun toString(): String {
        return "UserEntity(userId=$userId, email=$email, password=$password, created=$created, authorities=$authorities)"
    }


}