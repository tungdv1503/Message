package com.ph25579.nhantin.model

import com.google.firebase.Timestamp

class Messager {
    var massage: String = ""
    var uidSender: String = ""
    var uidReceiver: String = ""
    lateinit var time :Timestamp

    constructor(massage: String, uidSender: String, uidReceiver: String,time:Timestamp) {
        this.massage = massage
        this.uidSender = uidSender
        this.uidReceiver = uidReceiver
        this.time = time
    }
}