package com.ph25579.nhantin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
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
    private lateinit var imgBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        initData()
    }

    fun initViews() {
        imgBack = findViewById(R.id.img_back)
        tvEmail = findViewById(R.id.editTextEmail)
        tvPassword = findViewById(R.id.editTextPassword)
        btnLogin = findViewById(R.id.btn_submit)
    }

    fun initData() {
//        auth = FirebaseAuth.getInstance()
        auth = Firebase.auth
        btnLogin.setOnClickListener { view ->
            login(tvEmail.text.toString().trim(), tvPassword.text.toString().trim())
        }
        imgBack.setOnClickListener {
                var i = Intent(this, HomeActivity::class.java)
                startActivity(i)
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
                Toast.makeText(this, task.isSuccessful.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}