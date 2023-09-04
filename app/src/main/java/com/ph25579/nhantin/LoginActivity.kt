package com.ph25579.nhantin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    lateinit var tvEmail: TextView
    lateinit var tvPassword: TextView
    lateinit var btnLogin: Button
    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        initData()
    }

    fun initViews() {
        tvEmail = findViewById(R.id.editTextEmail)
        tvPassword = findViewById(R.id.editTextPassword)
        btnLogin = findViewById(R.id.btn_submit)
    }

    fun initData() {
//        auth = FirebaseAuth.getInstance()
        auth = Firebase.auth
        btnLogin.setOnClickListener { view ->
            login(tvEmail.text.toString(), tvPassword.text.toString())
        }
    }

    fun login(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                var i = Intent(this, FrendActivity::class.java)
                var a = Bundle()
                a.putString("uid", auth.uid)
                i.putExtra("bundle", a)
                startActivity(i)
            } else {
                Toast.makeText(this, "Email hoặc password không chính xác", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}