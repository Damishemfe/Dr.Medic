package com.hitrosttech.drmedic.view

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hitrosttech.drmedic.R

class SignIn : AppCompatActivity() {

	/*Progress Dialog*/
	private lateinit var progressDialog : ProgressDialog

    /*Firebase Auth*/
    private lateinit var firebaseAuth: FirebaseAuth
    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

		/*Configure Progress Dialog*/
		progressDialog = ProgressDialog(this)
		progressDialog.setTitle("Please wait")
		progressDialog.setMessage("Logging In...")
		progressDialog.setCanceledOnTouchOutside(false)

        /*Start Sign Up Activity*/
        val btnSignUp: TextView = findViewById(R.id.txt_sign_up)
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUp::class.java))
        }

        /*Initiate FireBase*/
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        /*Button CLicked*/
        val signIn: TextView = findViewById(R.id.sign_in)
        signIn.setOnClickListener {
            /*Before Logging, validate Data*/
            validateData()
        }

    }

    /*Get data from FireBase*/
    /*Check valid Data*/
    private fun validateData() {
        /*Get Data*/
        val etEmail: EditText = findViewById(R.id.et_email)
        val etPassword: EditText = findViewById(R.id.et_password)
        email = etEmail.text.toString().trim()
        password = etPassword.text.toString().trim()

        /*Validate Data*/
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            /*Invalidate Email Data*/
            etEmail.error = "Invalid Email"
        } else if (TextUtils.isEmpty(password)) {
            /*No Password Entered*/
            etPassword.error = "Please enter Password"
        } else {
            /*Valid Data*/
            /*Login User*/
			firebaseLogin()
        }
    }

	private fun firebaseLogin() {
		/*Show Progress Dialog*/
		progressDialog.show()
		firebaseAuth.signInWithEmailAndPassword(email, password)
			.addOnSuccessListener {
				/*Login Successful*/
				progressDialog.dismiss()
				/*Get User Info*/
				val firebaseUser = firebaseAuth.currentUser
				val email = firebaseUser!!.email
                Toast.makeText(this, "Logged In as $email", Toast.LENGTH_SHORT).show()

				/*Open Dashboard*/
				startActivity(Intent(this, MainActivity::class.java))
				finish()
			}
			.addOnFailureListener { e->
				/*Login Failed*/
				progressDialog.dismiss()
				Toast.makeText(this, "Login Failed due to ${e.message}", Toast.LENGTH_SHORT).show()
			}
	}

	/*Checking Database for User*/
    private fun checkUser() {
        /*IF User is already logged In*/
        /*Go to DashBoard Activity*/
        /*Get Current User*/
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            /*User Already logged In*/
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}