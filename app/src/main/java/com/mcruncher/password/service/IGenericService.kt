package com.mcruncher.password.service

import io.realm.RealmObject

/**
 * @author Madasamy
 * *
 * @version 1.0.0
 */

interface IGenericService<T : RealmObject> {
    fun create(`object`: T)

    fun update(`object`: T)

    fun findById(id: Long): T

    fun findByName(name: String): T

    fun findAll(): List<T>

    fun count(): Long

    fun deleteAll()

    fun deleteById(id: Long)

    fun delete(`object`: T)
}
