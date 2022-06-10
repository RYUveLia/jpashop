package jpabook.jpashop

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

interface MemberRepository : CrudRepository<Member, Long> {
    fun findByUsername(username: String): Member?
}