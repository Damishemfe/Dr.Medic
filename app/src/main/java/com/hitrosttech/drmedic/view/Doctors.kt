package com.hitrosttech.drmedic.view

import android.content.Intent
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.hitrosttech.drmedic.R

class Doctors : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_doctors)
		
		val btnDoc: RelativeLayout = findViewById(R.id.cardView3)
		btnDoc.setOnClickListener {
			startActivity(Intent(this, DoctorProfile::class.java))
		}
	}
}