package com.hitrosttech.drmedic.view

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.hitrosttech.drmedic.R

class SignUp : AppCompatActivity() {

	/*Progress Dialog*/
	private lateinit var progressDialog : ProgressDialog

	/*Firebase Auth*/
	private lateinit var firebaseAuth: FirebaseAuth
	private var firstName = ""
	private var lastName = ""
	private var phoneNumber = ""
	private var email = ""
	private var password = ""
	private var homeAddress = ""

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_sign_up)

		/*SIGN UP TO SIGN IN*/
		val txtSignIn: TextView = findViewById(R.id.txt_sign_in)
		txtSignIn.setOnClickListener{
			startActivity(Intent(this, SignIn::class.java))
		}

		/*Configure Progress Dialog*/
		progressDialog = ProgressDialog(this)
		progressDialog.setTitle("Please wait")
		progressDialog.setMessage("Creating your Profile..")
		progressDialog.setCanceledOnTouchOutside(false)

		/*Initiate Firebase Authentication*/
		firebaseAuth = FirebaseAuth.getInstance()

		/*Button Clicked*/
		val btnSignUp : TextView = findViewById(R.id.btn_sign_up)
		btnSignUp.setOnClickListener {
			/*Validate Data*/
			validateData()
		}

	}

	private fun validateData() {
		/*Get Data*/
		val etFirstName : EditText = findViewById(R.id.et_first_name)
		val etLastName : EditText = findViewById(R.id.et_last_name)
		val etEmail : EditText = findViewById(R.id.et_email)
		val etPhoneNum : EditText = findViewById(R.id.et_phone_num)
		val etPassword : EditText = findViewById(R.id.et_create_password)
		val etHomeAddress : EditText = findViewById(R.id.et_home_address)

		firstName = etFirstName.text.toString().trim()
		lastName = etLastName.text.toString().trim()
		email = etEmail.text.toString().trim()
		phoneNumber = etPhoneNum.text.toString().trim()
		password = etPassword.text.toString().trim()
		homeAddress = etHomeAddress.text.toString()

		/*Validate Data*/
		if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
			/*Invalidate Email Data*/
			etEmail.error = "Invalid Email"
		}
		else if (TextUtils.isEmpty(password)){
			etPassword.error = "Please enter Password"
		}
		else if (password.length < 6){
			etPassword.error = "Password less than 6.. Enter Valid password"
		}
		else if (TextUtils.isEmpty(firstName)){
			etFirstName.error = "Please enter First Name"
		}
		else if (TextUtils.isEmpty(lastName)){
			etLastName.error = "Please enter Last Name"
		}
		else if (TextUtils.isEmpty(phoneNumber)){
			etPhoneNum.error = "Please enter Phone Number"
		}
		else if (TextUtils.isEmpty(homeAddress)){
			etHomeAddress.error = "Please enter Home Address"
		}
		else {
			/*Data is Valid*/
			/*Proceed Sign Up*/
			firebaseSignUp()
		}
	}

	private fun firebaseSignUp() {
		/*Show Progress bar*/
		progressDialog.show()

		/*Create User Profile*/
		firebaseAuth.createUserWithEmailAndPassword(email, password)
			.addOnSuccessListener {
				/*SignUp Successful*/
				sendVerificationEmail()
				progressDialog.dismiss()

				/*Get Current User*/
				val firebaseUser = firebaseAuth.currentUser
				val email = firebaseUser!!.email
				Toast.makeText(this, "Account created with email $email", Toast.LENGTH_SHORT).show()
			}
			.addOnFailureListener { e->
				/*SignUp failed*/
				progressDialog.dismiss()
				Toast.makeText(this, "SignUp failed due to ${e.message}", Toast.LENGTH_SHORT).show()
			}
	}

	private fun sendVerificationEmail() {
		val user = FirebaseAuth.getInstance().currentUser
		user?.sendEmailVerification()?.addOnCompleteListener { task: Task<Void?> ->
			if (task.isSuccessful) {
				Toast.makeText(this, "Check Your Email for Verification", Toast.LENGTH_SHORT).show()
				FirebaseAuth.getInstance().signOut()
			}
		}
	}
}