package com.example.handymanversion2

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val submitBtn = findViewById(R.id.submitBtn) as Button
        val email = findViewById(R.id.email) as TextView


        supportActionBar?.hide()

        submitBtn.setOnClickListener{
            val emailAddress : String = email.text.toString().trim{ it <= ' '}
            if(emailAddress.isEmpty()){
                Toast.makeText(
                    this@ForgotPassword,
                    "Please enter email address",
                    Toast.LENGTH_SHORT
                ).show()
            }else{
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener{task ->
                        if(task.isSuccessful){
                            Toast.makeText(
                                this@ForgotPassword,
                                "Email sent successfully to reset your password",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        }
                        else{
                            Toast.makeText(
                                this@ForgotPassword,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }

    }
}