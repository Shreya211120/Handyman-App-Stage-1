package com.example.handymanversion2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()


        val email_address_login = findViewById(R.id.email_address_login) as TextView
        val sign_up_now = findViewById(R.id. sign_up_now) as TextView
        val password_login = findViewById(R.id.password_login) as TextView
        val login_button = findViewById(R.id.login_button) as Button
        val forgotPassword = findViewById(R.id.forgotPassword) as TextView


        sign_up_now.setOnClickListener {
            startActivity(Intent(this@Login, SignUp::class.java))
        }

        forgotPassword.setOnClickListener{
            startActivity(Intent(this@Login,ForgotPassword::class.java))
        }


        login_button.setOnClickListener{
            when{
                TextUtils.isEmpty(email_address_login.text.toString().trim{it<=' '})->{
                    Toast.makeText(
                        this@Login,
                        "Please enter email.",
                        Toast.LENGTH_SHORT
                    ).show()

                }
                TextUtils.isEmpty(password_login.text.toString().trim{it<=' '})->{
                    Toast.makeText(
                        this@Login,
                        "Please enter password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else->{
                    val email:String = email_address_login .text.toString().trim{ it <= ' '}
                    val password:String = password_login.text.toString().trim{ it <= ' '}

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(
                            OnCompleteListener<AuthResult> { task ->
                                if (task.isSuccessful) {
                                    //Firebase registered user
                                    val firebaseUser: FirebaseUser = task.result!!.user!!
                                    Toast.makeText(
                                        this@Login,
                                        "You are registered successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    val intent =
                                        Intent(this@Login, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", FirebaseAuth.getInstance().currentUser!!.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()

                                } else {
                                    Toast.makeText(
                                        this@Login,
                                        task.exception!!.message.toString(),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        )
                }
            }
        }
    }
}





