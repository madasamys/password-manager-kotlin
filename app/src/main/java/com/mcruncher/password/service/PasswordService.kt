package com.mcruncher.password.service

import com.mcruncher.password.domain.Password

import java.util.Date

/**
 * Author : Madasamy
 * Version : 1.1.x
 */

class PasswordService : GenericService<Password>(Password::class.java), IPasswordService
{

    override fun create(password: Password)
    {
        val id = nextId
        password.id = id
        password.createdOn = Date()
        super.create(password)
    }

    override fun update(password: Password)
    {
        password.updatedOn = Date()
        super.update(password)
    }
}
