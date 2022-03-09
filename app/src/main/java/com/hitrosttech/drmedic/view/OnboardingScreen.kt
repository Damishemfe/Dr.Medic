package com.hitrosttech.drmedic.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.hitrosttech.drmedic.R

class OnboardingScreen : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_onboarding_screen)
		
		val getStarted: TextView =findViewById(R.id.get_started)
		
		/*Starting the next Activity from the button*/
		getStarted.setOnClickListener {
			startActivity(Intent(this, SignIn::class.java))
		}
	}
}