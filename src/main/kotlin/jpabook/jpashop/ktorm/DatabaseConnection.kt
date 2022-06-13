package jpabook.jpashop.ktorm

import org.ktorm.database.Database

object DatabaseConnection {
    val database: Database = Database.connect("jdbc:h2:tcp://localhost/~/jpashop", user = "sa", password = "")
}