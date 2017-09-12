package com.mcruncher.password.service;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.mcruncher.password.domain.Password;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.realm.Realm;
import io.realm.RealmConfiguration;

import static org.junit.Assert.*;

/**
 * Author : Madasamy
 * Version : 1.0.0
 */

@RunWith(AndroidJUnit4.class)
public class PasswordServiceTest
{
    private IPasswordService passwordService;
    private Password password;

    @Before
    public void setup()
    {
        Context appContext = InstrumentationRegistry.getTargetContext();
        Realm.init(appContext);
        RealmConfiguration config = new RealmConfiguration.Builder().name("test.realm").inMemory().build();
        Realm.setDefaultConfiguration(config);
        passwordService = new PasswordService();

        password = new Password("foo", "89890");
        passwordService.create(password);
    }

    @After
    public void tearDown()
    {
        passwordService.deleteAll();
    }


    @Test
    public void create() throws Exception
    {
        assertEquals(1, passwordService.count());
    }

    @Test
    public void update() throws Exception
    {
        password.setUserName("f001");
        passwordService.update(password);
        assertEquals(1, passwordService.count());
    }

    @Test
    public void testDeleteById()
    {
        passwordService.deleteById(password.getId());
        assertEquals(0, passwordService.count());
    }

    @Test
    public void testDelete()
    {
        Password password = passwordService.findById(this.password.getId());
        passwordService.delete(password);
        assertEquals(0, passwordService.count());
    }

}