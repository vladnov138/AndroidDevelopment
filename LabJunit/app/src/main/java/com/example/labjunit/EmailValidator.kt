package com.example.labjunit

object EmailValidator {

    fun validate(email: String): Boolean {
        return email.matches(Regex(".+@.+\\.[A-Za-z]+"))
    }
}