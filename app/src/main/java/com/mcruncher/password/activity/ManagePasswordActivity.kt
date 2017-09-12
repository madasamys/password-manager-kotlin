package com.mcruncher.password.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem

import com.mcruncher.password.R

import android.support.design.widget.TextInputLayout
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import com.mcruncher.password.domain.Password
import com.mcruncher.password.service.PasswordService


/**
 * Author : Madasamy
 * Version : 1.0.0
 */

class ManagePasswordActivity : AppCompatActivity()
{
    private var accountInputLayoutName: TextInputLayout? = null
    private var accountEditText: EditText? = null
    private var usernameInputLayout: TextInputLayout? = null
    private var userNameEditText: EditText? = null
    private var passwordInputLayoutName: TextInputLayout? = null
    private var passwordEditText: EditText? = null
    private var passwordService = PasswordService()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_password_layout)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setAccountName()
        setUsername()
        setPassword()
    }

    private fun setAccountName()
    {
        accountInputLayoutName = findViewById(R.id.input_layout_account) as TextInputLayout
        accountEditText = findViewById(R.id.account_name_edit_text) as EditText
        accountEditText!!.addTextChangedListener(MyTextWatcher(accountEditText))
    }

    private fun setUsername()
    {
        usernameInputLayout = findViewById(R.id.input_layout_username) as TextInputLayout
        userNameEditText = findViewById(R.id.username_edit_text) as EditText
        userNameEditText!!.addTextChangedListener(MyTextWatcher(userNameEditText))
    }


    private fun setPassword()
    {
        passwordInputLayoutName = findViewById(R.id.input_layout_password) as TextInputLayout
        passwordEditText = findViewById(R.id.password_edit_text) as EditText
        passwordEditText!!.addTextChangedListener(MyTextWatcher(passwordEditText))
    }

    override fun onBackPressed()
    {
        super.onBackPressed()
        finish()
    }

    inner class MyTextWatcher(view: View?) : TextWatcher
    {
        var view: View? = null

        init
        {
            this.view = view
        }

        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int)
        {
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int)
        {
        }

        override fun afterTextChanged(editable: Editable)
        {
            when (view?.id)
            {
                R.id.account_name_edit_text ->
                {
                    validateName(accountEditText, accountInputLayoutName, getString(R.string.err_msg_name))
                }
                R.id.username_edit_text ->
                {
                    validateName(userNameEditText, usernameInputLayout, "Enter username")
                }
                R.id.password_edit_text ->
                {
                    validateName(passwordEditText, passwordInputLayoutName, "Enter password")
                }
            }
        }
    }

    private fun validateName(editText: EditText?, textInputLayout: TextInputLayout?, errorMessage: String): Boolean
    {
        if (editText?.text.toString().trim { it <= ' ' }.isEmpty())
        {
            textInputLayout?.error = errorMessage
            requestFocus(editText)
            return false
        } else
        {
            textInputLayout?.isErrorEnabled = false
        }
        return true
    }

    private fun requestFocus(view: View?)
    {

        if (view != null && view.requestFocus())
        {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.manage_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when (item.itemId)
        {
            android.R.id.home ->
            {
                finish()
                return true
            }
            R.id.save ->
            {
                save(item)
                return true
            }
            else -> return true
        }
    }

    private fun save(item: MenuItem)
    {
        if (!validateName(accountEditText, accountInputLayoutName, getString(R.string.err_msg_name))
                && !validateName(userNameEditText, usernameInputLayout, "Enter username") &&
                !validateName(passwordEditText, passwordInputLayoutName, "Enter password"))
        {
            return
        }
        val password = Password()
        password.accountName = accountEditText?.text.toString()
        password.userName = userNameEditText?.text.toString()
        password.password = passwordEditText?.text.toString()
        passwordService.create(password)
        finish()
    }

}
