package com.ph25579.nhantin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ph25579.nhantin.R
import com.ph25579.nhantin.model.User

class UserAdapter(private val userList: List<User>, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
    class UserViewHolder(itemView: View,private val listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name)

        //        val listener:LinearLayout = itemView.findViewById(R.id.listener)
        //Thêm các trường thông tin khác của người dùng nếu cần
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                   listener.onItemClick(position)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_frend, parent, false)
        return UserViewHolder(itemView,listener)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.emailTextView.text = currentUser.email
        holder.nameTextView.text = currentUser.name
        //Cập nhật các trường thông tin khác của người dùng nếu cần
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}