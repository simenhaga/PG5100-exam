package no.kristiania.exam.models.entities

import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "animals", schema = "public")
class AnimalEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animals_animal_id_seq")
    @SequenceGenerator(
        name = "animals_animal_id_seq",
        allocationSize = 1
    )
    @Column(name = "animal_id", nullable = false)
    val animalId: Long? = null,

    @Column(name = "animal_name")
    val name: String,

    @Column(name = "animal_type")
    val animalType: String,

    @Column(name = "animal_age")
    val animalAge: Int,

    @Column(name = "vaccinated")
    val vaccinated: Boolean,

    @Column(name = "checked_in")
    val checkInTime: LocalDateTime = LocalDateTime.now(),

    @Column(name = "checked_out")
    val checkOutTime: LocalDateTime? = null
) {
    override fun toString(): String {
        return "AnimalEntity(animalId=$animalId, name='$name', animalType='$animalType', animalAge=$animalAge, vaccinated=$vaccinated, checkInTime=$checkInTime, checkOutTime=$checkOutTime)"
    }
}