package com.mcruncher.password.service

import io.realm.Case
import io.realm.Realm
import io.realm.RealmObject
import io.realm.Sort

/**
 * @author Madasamy
 * @version 1.0.0
 */

open class GenericService<T : RealmObject>(internal var objectClass: Class<T>) : IGenericService<T>
{
    internal var realm: Realm

    init
    {
        this.realm = Realm.getDefaultInstance()
    }

    override fun create(`object`: T)
    {
        save(`object`)
    }

    override fun update(`object`: T)
    {
        save(`object`)
    }

    private fun save(`object`: T)
    {
        realm.executeTransaction { realm -> realm.insertOrUpdate(`object`) }
    }

    override fun findById(id: Long): T
    {
        return realm.where(objectClass).equalTo("id", id).findFirst()
    }

    override fun findByName(name: String): T
    {
        return realm.where(objectClass).equalTo("name", name, Case.INSENSITIVE).findFirst()
    }

    override fun findAll(): List<T>
    {
        return realm.where(objectClass).findAllSorted("updatedOn", Sort.DESCENDING)
    }

    override fun count(): Long
    {
        return realm.where(objectClass).count()
    }

    override fun deleteAll()
    {
        realm.executeTransaction { realm -> realm.delete(objectClass) }
    }

    override fun deleteById(id: Long)
    {
        val realmObject = findById(id)
        realm.executeTransaction { realmObject.deleteFromRealm() }
    }

    override fun delete(`object`: T)
    {
        realm.executeTransaction { `object`.deleteFromRealm() }
    }

    internal val nextId: Long
        get()
        {
            val currentIdNum = realm.where(objectClass).max("id")
            val nextId: Long
            if (currentIdNum == null)
            {
                nextId = 1
            } else
            {
                nextId = currentIdNum.toLong() + 1
            }
            return nextId
        }
}
