package com.example.befit

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class WorkoutLogActivity : AppCompatActivity() {

    private lateinit var workoutLogContainer: LinearLayout
    private lateinit var addWorkoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_log)

        workoutLogContainer = findViewById(R.id.workoutLogContainer)
        addWorkoutButton = findViewById(R.id.addWorkoutButton)

        // Load and display saved workouts
        loadSavedWorkouts()

        // Optionally handle new workout addition or actions
        addWorkoutButton.setOnClickListener {
            Toast.makeText(this, "Navigate to New Workout screen!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadSavedWorkouts() {
        val sharedPreferences = getSharedPreferences("BeFitWorkouts", Context.MODE_PRIVATE)
        val savedWorkout = sharedPreferences.getString("latestWorkout", null)

        if (savedWorkout.isNullOrEmpty()) {
            Toast.makeText(this, "No workouts found. Start logging your workouts!", Toast.LENGTH_SHORT).show()
        } else {
            // Parse and display saved workout data
            val workouts = savedWorkout.split(";").map { workout ->
                workout.replace("{", "").replace("}", "").split(", ").associate {
                    val (key, value) = it.split("=")
                    key to value
                }
            }

            workouts.forEach { workout ->
                addWorkoutCard(workout)
            }
        }
    }

    private fun addWorkoutCard(workout: Map<String, String>) {
        val inflater = layoutInflater
        val workoutCard = inflater.inflate(R.layout.card_workout_log, workoutLogContainer, false) as CardView

        val exerciseNameTextView = workoutCard.findViewById<TextView>(R.id.tv_exercise_name)
        val setsTextView = workoutCard.findViewById<TextView>(R.id.et_sets)
        val weightTextView = workoutCard.findViewById<TextView>(R.id.et_kgs)

        exerciseNameTextView.text = workout["exerciseName"]
        setsTextView.text = "Sets: ${workout["sets"]}"
        weightTextView.text = "Weight: ${workout["weight"]} kg"

        workoutLogContainer.addView(workoutCard)
    }
}
