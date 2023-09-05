package com.ph25579.nhantin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    lateinit var tvEmail: TextView
    lateinit var tvPassword: TextView
    lateinit var tvName: TextView
    lateinit var btnSignin: Button
    lateinit var auth: FirebaseAuth
    private lateinit var imgBack: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
        initData()
    }

    fun initViews() {
        imgBack = findViewById(R.id.img_back)
        tvEmail = findViewById(R.id.editTextEmail)
        tvPassword = findViewById(R.id.editTextPassword)
        btnSignin = findViewById(R.id.btn_submit)
        tvName = findViewById(R.id.editTextName)
    }

    fun initData() {

//        auth = FirebaseAuth.getInstance()
        auth = Firebase.auth
        btnSignin.setOnClickListener {
            if (tvEmail.text.toString().equals("") && tvPassword.text.toString()
                    .equals("") && tvName.text.toString().equals("")
            ) {
                Toast.makeText(
                    this, "Không được bỏ trống", Toast.LENGTH_SHORT
                ).show()
            } else if (tvPassword.text.length < 6) {
                Toast.makeText(
                    this, "Password >= 6 kí tự", Toast.LENGTH_SHORT
                ).show()
            } else {
                signin(
                    tvEmail.text.toString().trim(),
                    tvPassword.text.toString().trim(),
                    tvName.text.toString().trim()
                )
            }

        }
        imgBack.setOnClickListener {
            var i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }
    }

    fun signin(email: String, password: String, name: String) {
        val db = Firebase.firestore
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            Toast.makeText(
                this, task.isSuccessful.toString() + task.exception.toString(), Toast.LENGTH_SHORT
            ).show()
            Log.e("eee", task.exception.toString())
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "createUserWithEmail:success")
                val user = auth.currentUser
                var uid = user?.uid.toString()
                var hmap = HashMap<String, String>()
                hmap.put("uid", uid)
                hmap.put("email", email)
                hmap.put("name", name)
                db.collection("Users").add(hmap).addOnSuccessListener { documentReference ->
                    Log.d("id", "DocumentSnapshot added with ID: ${documentReference.id}")
                    var i = Intent(this, LoginActivity::class.java)
                    startActivity(i)
                }
                    .addOnFailureListener { e ->
                        Log.w("id", "Error adding document", e)
                    }
            } else {
                Log.w("TAG", "createUserWithEmail:failure", task.exception)
                Toast.makeText(
                    baseContext,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}