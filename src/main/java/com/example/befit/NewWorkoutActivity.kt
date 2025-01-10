package com.example.befit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class NewWorkoutActivity : AppCompatActivity() {

    private lateinit var btnAddExercise: Button
    private lateinit var btnSaveWorkout: Button
    private lateinit var exerciseContainer: LinearLayout
    private val selectedExercises = mutableListOf<String>() // List to store selected exercises

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_workout)

        btnAddExercise = findViewById(R.id.btn_add_exercise)
        btnSaveWorkout = findViewById(R.id.btn_save_workout)
        exerciseContainer = findViewById(R.id.exercise_container)

        // Set up the Add Exercise button
        btnAddExercise.setOnClickListener {
            val intent = Intent(this, AddExerciseActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_EXERCISE)
        }

        // Set up the Save Workout button
        btnSaveWorkout.setOnClickListener {
            saveWorkout()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_EXERCISE && resultCode == Activity.RESULT_OK) {
            val exerciseName = data?.getStringExtra("EXERCISE_NAME")
            if (exerciseName != null) {
                addExerciseCard(exerciseName)  // Add exercise to the container
            } else {
                Toast.makeText(this, "No exercise selected", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Add exercise card to the container
    private fun addExerciseCard(exerciseName: String) {
        // Inflate the exercise card layout (exercise_card.xml)
        val exerciseCard = layoutInflater.inflate(R.layout.exercise_card, exerciseContainer, false)

        // Set the exercise name in the card
        val exerciseNameInput = exerciseCard.findViewById<EditText>(R.id.exerciseNameInput)
        exerciseNameInput.setText(exerciseName)

        // Find the set container in the exercise card (where sets will be added)
        val setContainer = exerciseCard.findViewById<LinearLayout>(R.id.set_container)

        // Add the initial set to the set container
        addSetCard(setContainer)

        // Find the Add Set button and set its click listener
        val btnAddSet = exerciseCard.findViewById<Button>(R.id.btn_add_set)
        btnAddSet.setOnClickListener {
            addSetCard(setContainer) // Add another set dynamically when clicked
        }

        // Add the exercise card to the main container
        exerciseContainer.addView(exerciseCard)
    }

    // Add a set card to the set container
    private fun addSetCard(setContainer: LinearLayout) {
        // Inflate the set card layout (card_set.xml)
        val setCard = layoutInflater.inflate(R.layout.card_set, setContainer, false)

        // Get the EditTexts for sets and kgs in the set card
        val setsInput = setCard.findViewById<EditText>(R.id.et_sets)
        val weightInput = setCard.findViewById<EditText>(R.id.et_kgs)

        // Add the set card to the set container
        setContainer.addView(setCard)
    }

    // Save workout logic
    private fun saveWorkout() {
        if (exerciseContainer.childCount == 0) {
            Toast.makeText(this, "No exercises to save!", Toast.LENGTH_SHORT).show()
            return
        }

        // Iterate through the exercise cards and collect the data
        for (i in 0 until exerciseContainer.childCount) {
            val exerciseCard = exerciseContainer.getChildAt(i) as CardView
            val exerciseName = exerciseCard.findViewById<EditText>(R.id.exerciseNameInput).text.toString()

            // Collect sets from the set container within the exercise card
            val setContainer = exerciseCard.findViewById<LinearLayout>(R.id.set_container)
            val sets = mutableListOf<Pair<Int, Float>>()
            for (j in 0 until setContainer.childCount) {
                val setCard = setContainer.getChildAt(j) as LinearLayout
                val setsInput = setCard.findViewById<EditText>(R.id.et_sets).text.toString().toIntOrNull()
                val weightInput = setCard.findViewById<EditText>(R.id.et_kgs).text.toString().toFloatOrNull()
                if (setsInput != null && weightInput != null) {
                    sets.add(Pair(setsInput, weightInput))
                }
            }

            // Add logic to save exercise data (e.g., to SharedPreferences, database, or any storage solution)
            // For now, just log the collected data
            println("Exercise: $exerciseName, Sets: $sets")
        }

        // Clear the container and selected exercises after saving
        exerciseContainer.removeAllViews()
        selectedExercises.clear()

        Toast.makeText(this, "Workout saved successfully!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUEST_CODE_ADD_EXERCISE = 1
    }
}
