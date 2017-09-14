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
import com.mcruncher.password.CommonConstants
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
    private var password = Password()
    private var editMode = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.manage_password_layout)
        initSetUp()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setAccountName()
        setUsername()
        setPassword()
        disableComponent(editMode)
    }

    private fun initSetUp()
    {
        val id = intent.getLongExtra(CommonConstants.ID_KEY, 0)
        password = passwordService.findById(id)
        editMode = !isEdit()
    }

    private fun setAccountName()
    {
        accountInputLayoutName = findViewById(R.id.input_layout_account) as TextInputLayout
        accountEditText = findViewById(R.id.account_name_edit_text) as EditText
        accountEditText!!.addTextChangedListener(MyTextWatcher(accountEditText))
        accountEditText!!.setText(password.accountName ?: "")
    }

    private fun setUsername()
    {
        usernameInputLayout = findViewById(R.id.input_layout_username) as TextInputLayout
        userNameEditText = findViewById(R.id.username_edit_text) as EditText
        userNameEditText!!.addTextChangedListener(MyTextWatcher(userNameEditText))
        userNameEditText!!.setText(password.userName ?: "")
    }


    private fun setPassword()
    {
        passwordInputLayoutName = findViewById(R.id.input_layout_password) as TextInputLayout
        passwordEditText = findViewById(R.id.password_edit_text) as EditText
        passwordEditText!!.addTextChangedListener(MyTextWatcher(passwordEditText))
        passwordEditText!!.setText(password.password ?: "")
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
        menu.findItem(R.id.save).title = if (editMode) getString(R.string.save) else getString(R.string.edit)
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
                return updateComponentsAndSave(item)
            }
            else -> return true
        }
    }

    private fun updateComponentsAndSave(item: MenuItem): Boolean
    {
        if (item.title == getString(R.string.edit))
        {
            item.title = getString(R.string.save)
            editMode = true
            disableComponent(editMode)
        } else
        {
            validateAndSave(item)
        }
        return true
    }

    private fun disableComponent(editMode: Boolean)
    {
        accountInputLayoutName!!.isEnabled = editMode
        accountEditText!!.isEnabled = editMode
        userNameEditText!!.isEnabled = editMode
        passwordEditText!!.isEnabled = editMode
    }

    private fun validateAndSave(item: MenuItem)
    {
        if (!validateName(accountEditText, accountInputLayoutName, getString(R.string.err_msg_name))
                && !validateName(userNameEditText, usernameInputLayout, getString(R.string.error_message_username)) &&
                !validateName(passwordEditText, passwordInputLayoutName, getString(R.string.error_message_password)))
        {
            return
        }
        savePassword()
        finish()
    }

    private fun savePassword()
    {
        val password = Password()
        password.accountName = accountEditText?.text.toString()
        password.userName = userNameEditText?.text.toString()
        password.password = passwordEditText?.text.toString()
        if (isEdit())
        {
            password.id = this.password.id
            passwordService.update(password)
        } else
        {
            passwordService.create(password)
        }
    }

    private fun isEdit() = password.id > 0

}
