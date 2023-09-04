package com.ph25579.nhantin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ph25579.nhantin.adapter.UserAdapter
import com.ph25579.nhantin.model.User

class FrendActivity : AppCompatActivity() {
    lateinit var listV: RecyclerView
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_frend)
        initViews()
        initData()
    }

    private fun initViews() {
        listV = findViewById(R.id.listViewAccounts)
    }

    private fun initData() {
        auth = Firebase.auth
        val layoutManager = LinearLayoutManager(this)
        listV.layoutManager = layoutManager
        fetchDataFromFirestore()
    }

    private fun fetchDataFromFirestore() {
        // Thực hiện truy vấn để lấy tất cả tài liệu từ bảng "Users"
        val user = auth.currentUser
        val db = Firebase.firestore

        db.collection("Users")
            .get()
            .addOnSuccessListener { documents ->
                val userList = mutableListOf<User>()
                val adapter = UserAdapter(userList, object : UserAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        nextScreen(userList.get(position))
                    }
                })
                // Lặp qua các tài liệu và lấy dữ liệu của mỗi tài liệu
                for (document in documents) {
                    val data = document.data
                    val name = data["name"] as String
                    val id = data["uid"] as String
                    val email = data["email"] as String
                    var users = User(id, email, name)
                    // Thêm dữ liệu vào danh sách
                    if (!id.equals(user?.uid.toString())) {
                        userList.add(users)
                    }
                }
                listV.adapter = adapter

            }
            .addOnFailureListener { exception ->
                // Xử lý lỗi nếu có
                Log.e("ee", "Error getting documents.", exception)
            }

    }

    fun nextScreen(user:User) {
        var i = Intent(this, MessengerActivity::class.java)
        var b = Bundle()
        b.putString("uid",user.id)
        b.putString("email",user.email)
        b.putString("name",user.name)
        i.putExtra("Bundle",b)
        startActivity(i)
    }
}