package jpabook.jpashop.ktorm

import org.ktorm.dsl.*
import org.ktorm.entity.*
import org.springframework.stereotype.Repository

@Repository
class ModelRepository {
    private val database = DatabaseConnection.database
    private val sequence = database.sequenceOf(Models)

    fun save(model: Model): Int {
        //val model = modelDto.convertModel()

        return try {
            sequence.add(model)
        } catch (e: Exception) {
            database.update(Models) {
                set(it.username, model.username)
                where {
                    it.id eq model.id!!
                }
            }
        }
    }

    fun find(id: Int): Model? {
        return sequence.find{it.id eq id}
    }
}