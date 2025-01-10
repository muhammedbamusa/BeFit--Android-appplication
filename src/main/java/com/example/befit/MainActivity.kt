package com.example.befit

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        findViewById<Button>(R.id.btn_new_workout).setOnClickListener {
            startActivity(Intent(this, NewWorkoutActivity::class.java))
        }

        findViewById<Button>(R.id.btn_workout_logs).setOnClickListener {
            // Implement navigation to workout logs
        }
    }
}
