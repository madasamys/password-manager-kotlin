package com.mcruncher.password.adapter

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import com.mcruncher.password.CommonConstants

import com.mcruncher.password.R
import com.mcruncher.password.activity.ManagePasswordActivity
import com.mcruncher.password.domain.Password
import io.realm.internal.Context

/**
 * Author : Madasamy
 * Version : 1.0.0
 */

class PasswordAdapter(var context: AppCompatActivity, private var passwordList: List<Password>) : RecyclerView.Adapter<PasswordAdapter.MyViewHolder>()
{

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var accountName: TextView
        var username: TextView
        var passwordRelativeLayout: RelativeLayout

        init
        {
            accountName = view.findViewById(R.id.accountname) as TextView
            username = view.findViewById(R.id.username) as TextView
            passwordRelativeLayout = view.findViewById(R.id.password_relative_layout) as RelativeLayout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.password_list_row, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val password = passwordList[position]
        holder.accountName.text = password.accountName
        holder.username.text = password.userName
        holder.passwordRelativeLayout.setOnClickListener { v -> onClick(password) }
    }

    private fun onClick(password: Password)
    {
        val managePasswordIntent = Intent(context, ManagePasswordActivity::class.java)
        managePasswordIntent.putExtra(CommonConstants.ID_KEY, password.id)
        context.startActivity(managePasswordIntent)
    }

    override fun getItemCount() = passwordList.size


}
