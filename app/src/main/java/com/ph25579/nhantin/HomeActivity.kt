package com.ph25579.nhantin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class HomeActivity : AppCompatActivity() {
    lateinit var btnLogin: Button
    lateinit var btnRegister: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViews()
        initData()
    }

    private fun initViews() {
        btnRegister = findViewById(R.id.btn_register)
        btnLogin = findViewById(R.id.btn_login)
    }

    private fun initData() {
        btnRegister.setOnClickListener {
            finish()
            var i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
        btnLogin.setOnClickListener {
            finish()
            var i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }
    }
}