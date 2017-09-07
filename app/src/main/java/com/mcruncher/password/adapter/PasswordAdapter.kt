package com.mcruncher.password.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.mcruncher.password.R
import com.mcruncher.password.domain.Password

/**
 * Author : Madasamy
 * Version : x.x.x
 */

class PasswordAdapter(private val passwordList: List<Password>) : RecyclerView.Adapter<PasswordAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var accountName: TextView
        var username: TextView

        init {
            accountName = view.findViewById(R.id.accountname) as TextView
            //            password = (TextView) view.findViewById(R.id.username);
            username = view.findViewById(R.id.username) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.password_list_row, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = passwordList[position]
        holder.accountName.text = movie.accountName
        holder.username.text = movie.userName
        // holder.password.setText(movie.getPassword());
    }

    override fun getItemCount(): Int {
        return passwordList.size
    }
}
