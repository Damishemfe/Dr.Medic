package com.hitrosttech.drmedic.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.hitrosttech.drmedic.R

class SplashScreenActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_splash_screen)
		
		/*Hiding the Action Bar*/
		window.setFlags(
			WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN
		)
		
		/*Starting the Next Activity*/
		Handler().postDelayed(
			{
				startActivity(Intent(this, OnboardingScreen::class.java))
			},
			/*Splash Screen Runtime*/
			2500
		)
	}
}