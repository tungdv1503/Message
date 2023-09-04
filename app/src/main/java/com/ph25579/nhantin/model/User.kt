package com.ph25579.nhantin.model

class User {
    var id: String = ""
    var email: String = ""
    var name:String=""

    constructor(id: String, email: String,name:String) {
        this.id = id
        this.email = email
        this.name=name
    }

}