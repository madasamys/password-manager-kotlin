package com.mcruncher.password.domain

import java.util.Date

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Author : Madasamy
 * Version : x.x.x
 */

open class Password : RealmObject {
    @PrimaryKey
    var id: Long = 0
    var accountName: String? = null
    var userName: String? = null
    var password: String? = null
    var createdOn: Date? = null
    var updatedOn: Date? = null

    constructor() {

    }

    constructor(userName: String, password: String) {
        this.userName = userName
        this.password = password
    }

    override fun toString(): String {
        return "Password{" +
                "id=" + id +
                ", accountName='" + accountName + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}'
    }

    override fun equals(`object`: Any?): Boolean {
        if (`object` is Password) return true
        if (`object` == null || javaClass != `object`.javaClass) return false
        val otherObject = `object` as Password?
        return userName == otherObject!!.userName && password == otherObject.password

    }

    override fun hashCode(): Int {
        var result = userName!!.hashCode()
        result = 31 * result + password!!.hashCode()
        return result
    }
}
