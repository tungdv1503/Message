package com.ph25579.nhantin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ServerValue
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ph25579.nhantin.adapter.messageAdapter
import com.ph25579.nhantin.model.Messager

class MessengerActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var messageListView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var imgBack: ImageView
    private lateinit var tvName: TextView
    private lateinit var listMessage: ArrayList<Messager>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messenger)
        initViews()
        initData()
    }

    private fun initViews() {
        messageListView = findViewById(R.id.messageListView)
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)
        imgBack = findViewById(R.id.img_back)
        tvName = findViewById(R.id.tv_name)
    }

    private fun initData() {
        auth = Firebase.auth
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true
        messageListView.layoutManager = layoutManager
        messageListView.setHasFixedSize(true)

        var b = intent.getBundleExtra("Bundle")
        var name = b?.getString("name", "")
        var uidReceiver = b?.getString("uid").toString()
        tvName.text = name
        val user = auth.currentUser
        var uidSender = user?.uid.toString()
        sendButton.setOnClickListener {
            var messageText = messageEditText.text.toString().trim()
            if(!messageText.equals("")){
                sendtext(messageText, uidSender, uidReceiver)
            }
            messageEditText.text.clear()
        }
        readtext(uidSender, uidReceiver)
        load(uidSender, uidReceiver)
        imgBack.setOnClickListener{
            finish()
        }
    }

    private fun sendtext(massge: String, uidSender: String, uidReceiver: String) {
        val db = Firebase.firestore
        var hmap = HashMap<String, Any>()
        hmap.put("massge", massge)
        hmap.put("uidSender", uidSender)
        hmap.put("uidReceiver", uidReceiver)
        hmap["timestamp"] = FieldValue.serverTimestamp()
        db.collection("Chats").add(hmap).addOnSuccessListener { documentReference ->
            readtext(uidSender, uidReceiver)
        }.addOnFailureListener { e ->
            Log.w("id", "Error adding document", e)
        }

    }

    private fun readtext(uidSender: String, uidReceiver: String) {
        listMessage = ArrayList()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Chats")
        collectionRef.orderBy("timestamp", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                listMessage.clear()
                for (document in documents) {
                    // Xử lý tài liệu tại đây
                    val data1 = document.data

                    if (data1 != null) {
                        val text = data1["massge"] as String
                        val uid1 = data1["uidSender"] as String
                        val uid2 = data1["uidReceiver"] as String
                        val time = data1.get("timestamp")
                        if (uid1.equals(uidSender) && uid2.equals(uidReceiver) || uid1.equals(
                                uidReceiver
                            ) && uid2.equals(uidSender)
                        ) {
                            if (time != null && time is Timestamp){
                                var msg = Messager(
                                    text,
                                    uid1,
                                    uid2,
                                    time as Timestamp
                                )
                                listMessage.add(msg)
                            }


                        }
//                                val sortedList = listMessage.sortedBy {  }
                    }
                    val adapter = messageAdapter(uidSender, listMessage)
                    messageListView.adapter = adapter
                }

            }
    }

    fun load(uidSender: String, uidReceiver: String) {
        val db = Firebase.firestore
        val collectionRef = db.collection("Chats")
        collectionRef.addSnapshotListener { snapshots, e ->
            if (e != null) {
                // Xử lý lỗi nếu có
                return@addSnapshotListener
            }
            if (snapshots != null && !snapshots.isEmpty) {
                readtext(uidSender,uidReceiver)
            } else {
                // Bảng không có tài liệu hoặc không tồn tại
            }
        }
    }
}
