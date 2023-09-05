package com.ph25579.nhantin.model

class User {
    var id: String = ""
        get() {
            return field
        }
        set(value) {
            field = value
        }
    var email: String = ""
        get() {
            return field
        }
        set(value) {
            field = value
        }
    var name: String = ""
        get() {
            return field
        }
        set(value) {
            field = value
        }

    constructor(id: String, email: String, name: String) {
        this.id = id
        this.email = email
        this.name = name
    }

}