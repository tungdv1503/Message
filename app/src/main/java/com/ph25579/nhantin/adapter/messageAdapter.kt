package com.ph25579.nhantin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.ph25579.nhantin.R
import com.ph25579.nhantin.model.Message

class messageAdapter(var auth: FirebaseAuth,val list: ArrayList<Message>) :
    RecyclerView.Adapter<messageAdapter.ViewHolder>() {
    val LEFT = 0
    val RIGHT = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == RIGHT) {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_messenger_right, parent, false)
            return ViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_messenger_left, parent, false)
            return ViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = list[position]
        holder.tvMessage.text = message.massage
    }

    override fun getItemViewType(position: Int): Int {
        val user = auth.currentUser
        var uidSender = user?.uid.toString()
        if (list.get(position).uidSender.equals(uidSender)) {
            return RIGHT
        } else {
            return LEFT
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return list.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessage: TextView = itemView.findViewById(R.id.tv_messenger)
    }
}