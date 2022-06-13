package jpabook.jpashop.ktorm

import org.ktorm.entity.Entity
import org.ktorm.schema.*

object Models : Table<Model>("MODEL") {
    val id = int("id").primaryKey().bindTo { it.id }
    val username = varchar("username").bindTo { it.username }
}

interface Model: Entity<Model> {
    companion object: Entity.Factory<Model>()
    var id: Int?
    var username:String
}
