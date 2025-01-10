package com.example.befit
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class WorkoutEntryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_entry)

        val workoutNameInput: EditText = findViewById(R.id.workoutNameInput)
        val setsInput: EditText = findViewById(R.id.setsInput)
        val repsInput: EditText = findViewById(R.id.repsInput)
        val weightInput: EditText = findViewById(R.id.weightInput)
        val selectDateButton: Button = findViewById(R.id.selectDateButton)
        val saveWorkoutButton: Button = findViewById(R.id.saveWorkoutButton)

        // Retrieve the selected exercise
        val selectedExercise = intent.getStringExtra("selectedExercise")
        if (selectedExercise != null) {
            workoutNameInput.setText(selectedExercise)
        }

        var selectedDate = ""
        selectDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                selectDateButton.text = selectedDate
            }, year, month, day).show()
        }

        saveWorkoutButton.setOnClickListener {
            val workoutName = workoutNameInput.text.toString()
            val sets = setsInput.text.toString()
            val reps = repsInput.text.toString()
            val weight = weightInput.text.toString()

            if (workoutName.isNotEmpty() && sets.isNotEmpty() && reps.isNotEmpty() && weight.isNotEmpty()) {
                Toast.makeText(this, "Workout Saved: $workoutName on $selectedDate", Toast.LENGTH_LONG).show()
                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
