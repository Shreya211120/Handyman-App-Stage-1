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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    // get reference to button
    //private lateinit var binding:SignUp

    private lateinit var authen: FirebaseAuth
    private lateinit var sign_up_button: Button
    private lateinit var enter_email: EditText
    private lateinit var enter_name: EditText
    private lateinit var enter_password: EditText
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        authen = FirebaseAuth.getInstance()
        sign_up_button = findViewById(R.id.sign_up_button)
        enter_email = findViewById(R.id.enter_email)
        enter_name = findViewById(R.id.enter_name)
        enter_password = findViewById(R.id.enter_password)
        val login_Btn = findViewById(R.id.login_Btn) as Button
        //database = FirebaseDatabase.getInstance().getReference("Users")

        sign_up_button.setOnClickListener {
            val name = enter_name.text.toString()
            val email = enter_email.text.toString()
            val password = enter_password.text.toString()

            signUp(name,email,password)
        }

        login_Btn.setOnClickListener {
            ///startActivity(Intent(this@SignUp,Login::class.java))
            onBackPressed()}
    }

    private fun signUp(name: String,email: String, password: String) {
        authen.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, authen.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this@SignUp, "Error Occured & Try Again", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun addUserToDatabase(name: String, email: String, uid: String){

        mDbRef = FirebaseDatabase.getInstance().getReference()

        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}

