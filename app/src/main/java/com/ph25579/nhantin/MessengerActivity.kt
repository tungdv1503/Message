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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ph25579.nhantin.adapter.messageAdapter

class MessengerActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    private lateinit var messageListView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: Button
    private lateinit var imgBack: ImageView
    private lateinit var tvName: TextView
    private lateinit var listMessage: ArrayList<com.ph25579.nhantin.model.Message>
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

            sendtext(messageText, uidSender, uidReceiver)
            messageEditText.text.clear()
        }
        readtext(uidSender, uidReceiver)
    }

    private fun sendtext(massge: String, uidSender: String, uidReceiver: String) {
        val db = Firebase.firestore
        var hmap = HashMap<String, String>()
        hmap.put("massge", massge)
        hmap.put("uidSender", uidSender)
        hmap.put("uidReceiver", uidReceiver)
        db.collection("Chats").add(hmap).addOnSuccessListener { documentReference ->
            readtext(uidSender, uidReceiver)
        }
            .addOnFailureListener { e ->
                Log.w("id", "Error adding document", e)
            }
    }

    private fun readtext(uidSender: String, uidReceiver: String) {
        listMessage = ArrayList()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("Chats")
        collectionRef.addSnapshotListener { snapshots, e ->
            listMessage.clear()
            if (e != null) {
                // Xử lý lỗi nếu có
                return@addSnapshotListener
            }

            if (snapshots != null && !snapshots.isEmpty) {
                // Dữ liệu trong bảng đã thay đổi
                // Bạn có thể truy cập danh sách các tài liệu được thay đổi bằng snapshots.documents
                for (document in snapshots.documents) {
                    // Xử lý tài liệu tại đây
                    val data1 = document.data


                    if (data1?.get("uidSender").toString().equals(uidSender) && data1?.get("uidReceiver").toString().equals(uidReceiver) || data1?.get("uidSender").toString().equals(
                            uidReceiver
                        ) && data1?.get("uidReceiver").toString().equals(uidSender)
                    ) {
                        var msg = com.ph25579.nhantin.model.Message(data1?.get("massge").toString(),data1?.get("uidSender").toString(),data1?.get("uidReceiver").toString())
                        listMessage.add(msg)
                    }
                    val adapter = messageAdapter(auth,listMessage)
                    messageListView.adapter = adapter
                }
            } else {
                // Bảng không có tài liệu hoặc không tồn tại
            }
        }
    }

}