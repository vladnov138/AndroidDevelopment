package com.example.labjunit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginEdit = findViewById<EditText>(R.id.loginEdit)
        val passEdit = findViewById<EditText>(R.id.passwordEdit)

        val loginButton = findViewById<Button>(R.id.button)

        loginButton.setOnClickListener {
            val email = loginEdit.text.toString()
            val password = passEdit.text.toString()

            if (EmailValidator.validate(email) && password.length > 5) {

            } else {
                Toast.makeText(this, "Wrong email or password", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}