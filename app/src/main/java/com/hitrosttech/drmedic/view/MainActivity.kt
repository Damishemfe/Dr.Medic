package com.hitrosttech.drmedic.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RelativeLayout
import com.hitrosttech.drmedic.R

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		val btnDoc :RelativeLayout = findViewById(R.id.cardView3)
		btnDoc.setOnClickListener {
			startActivity(Intent(this, Doctors::class.java))
		}
	}
}