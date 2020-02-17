package com.daffodil.renters.model.postables

import android.os.Bundle

open class User {

    companion object {
        val KEY_ID = "id"
        val KEY_FIRST_NAME = "firstName"
        val KEY_LAST_NAME = "lastName"
        val KEY_PHONE_NUMBER = "phoneNumber"
        val KEY_EMAIL = "email"
        val KEY_PASSWORD = "password"
    }

    var id: Long? = 0
    var firstName: String? = null
    var lastName: String? = null
    var phoneNumber: String? = null

    // App specific members. Server has no knowledge of these values.
    var email: String? = null
    var password: String? = null

    val bundle: Bundle
        get() {
            val b = Bundle()
            if (id != null) b.putLong(KEY_ID, id!!)
            if (firstName != null) b.putString(KEY_FIRST_NAME, firstName)
            if (lastName != null) b.putString(KEY_LAST_NAME, lastName)
            if (phoneNumber != null) b.putString(KEY_PHONE_NUMBER, phoneNumber)
            if (email != null) b.putString(KEY_EMAIL, email)
            if (password != null) b.putString(KEY_PASSWORD, password)
            return b
        }

}