package no.kristiania.exam.repos

import no.kristiania.exam.models.entities.AnimalEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AnimalRepo: JpaRepository<AnimalEntity, Long> {

    fun findByName(name: String?): AnimalEntity
}