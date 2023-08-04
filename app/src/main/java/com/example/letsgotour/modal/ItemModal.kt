package com.example.letsgotour.modal

class ItemModal {
    var name: String? = null
    var price: String? = null
    var startDes: String? = null
    var endDes: String? = null
    var day: String? = null
    var person: String? = null
    var description: String? = null
    var profile: String? = null
    var uid: String? = null
    var latitude: Float? = null
    var longitude: Float? = null

    constructor() {}

    constructor(
        name: String,
        price: String,
        startDes: String,
        endDes: String,
        day: String,
        person: String,
        description: String,
        profile: String?,
        uid: String,
        latitude: Float,
        longitude: Float
    ) {
        this.name = name
        this.price = price
        this.startDes = startDes
        this.endDes = endDes
        this.day = day
        this.person = person
        this.description = description
        this.profile = profile
        this.uid = uid
        this.latitude=latitude
        this.longitude=longitude
    }
}