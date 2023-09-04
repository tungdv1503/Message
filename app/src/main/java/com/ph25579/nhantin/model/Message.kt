package com.ph25579.nhantin.model

class Message {
    var massage: String = ""
    var uidSender: String = ""
    var uidReceiver: String = ""

    constructor(massage: String, uidSender: String, uidReceiver: String) {
        this.massage = massage
        this.uidSender = uidSender
        this.uidReceiver = uidReceiver
    }
}