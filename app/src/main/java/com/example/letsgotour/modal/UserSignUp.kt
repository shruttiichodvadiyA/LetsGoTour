package com.example.letsgotour.modal

import java.util.*

class UserSignUp {
    var name: String? = null
    var email: String? = null
    var uid : String ?=null

    constructor() {}
    constructor(name: String, email: String, uid: UUID) {
        this.name = name
        this.email = email
        this.uid=uid.toString()
    }
}