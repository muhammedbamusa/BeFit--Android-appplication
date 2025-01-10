package com.example.befit
import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class WorkoutListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)

        val exercises = listOf("Bench Press", "Deadlift", "Squat", "Pull-up", "Bicep Curl")
        val workoutListView: ListView = findViewById(R.id.workoutListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, exercises)
        workoutListView.adapter = adapter

        workoutListView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedExercise = exercises[position]
            val intent = Intent(this, WorkoutEntryActivity::class.java)
            intent.putExtra("selectedExercise", selectedExercise)
            startActivity(intent)
        }

        val addWorkoutButton: Button = findViewById(R.id.addWorkoutButton)
        addWorkoutButton.setOnClickListener {
            startActivity(Intent(this, WorkoutEntryActivity::class.java))
        }
    }
}
