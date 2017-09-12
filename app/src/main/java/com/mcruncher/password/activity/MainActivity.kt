package com.mcruncher.password.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.MenuItem
import android.view.View

import com.mcruncher.password.R
import com.mcruncher.password.adapter.PasswordAdapter
import com.mcruncher.password.domain.Password
import com.mcruncher.password.service.IPasswordService
import com.mcruncher.password.service.PasswordService

import java.util.ArrayList

class MainActivity : AppCompatActivity()
{

    private val passwordList = ArrayList<Password>()
    private val passwordService = PasswordService()
    private var recyclerView: RecyclerView? = null
    private var passwordAdapter: PasswordAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        passwordAdapter = PasswordAdapter(passwordList)
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        val mLayoutManager = LinearLayoutManager(applicationContext)
        recyclerView!!.layoutManager = mLayoutManager
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = passwordAdapter
    }

    fun onTapAddPassword(view: View)
    {
        startActivity(Intent(this, ManagePasswordActivity::class.java))
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
            else -> return true
        }
    }

    override fun onResume()
    {
        super.onResume()
        val size = passwordList.size
        passwordList.clear()
        passwordAdapter!!.notifyItemRangeRemoved(0, size);
        passwordList.addAll(passwordService.findAll())
        passwordAdapter!!.notifyDataSetChanged()
    }

    override fun onBackPressed()
    {
        super.onBackPressed()
        finish()
    }
}
