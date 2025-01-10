package com.example.befit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ExerciseListActivity : AppCompatActivity() {

    private lateinit var exerciseListView: RecyclerView
    private lateinit var searchBar: SearchView
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var exerciseList: MutableList<Exercise>
    private lateinit var filteredExerciseList: MutableList<Exercise>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_list)

        exerciseListView = findViewById(R.id.exerciseListView)
        searchBar = findViewById(R.id.searchBar)

        // Initialize exercise list (in a real app, this could be fetched from a database or API)
        exerciseList = mutableListOf(
            Exercise("Push-up"),
            Exercise("Squat"),
            Exercise("Lunges"),
            Exercise("Bicep Curl"),
            Exercise("Pull-up"),
            Exercise("Plank"),
            Exercise("Mountain Climbers"),
            Exercise("Leg Press"),
            Exercise("Deadlift"),
            Exercise("Bench Press")
        )

        filteredExerciseList = exerciseList.toMutableList()

        // Set up RecyclerView with Adapter
        exerciseAdapter = ExerciseAdapter(filteredExerciseList) { selectedExercise ->
            // Handle item click, return selected exercise to NewWorkoutActivity
            val resultIntent = Intent().apply {
                putExtra("selected_exercise", selectedExercise.name)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        exerciseListView.layoutManager = LinearLayoutManager(this)
        exerciseListView.adapter = exerciseAdapter

        // Search functionality
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filteredExerciseList.clear()
                if (newText.isNullOrEmpty()) {
                    filteredExerciseList.addAll(exerciseList)
                } else {
                    filteredExerciseList.addAll(exerciseList.filter {
                        it.name.contains(newText, ignoreCase = true)
                    })
                }
                exerciseAdapter.notifyDataSetChanged()
                return true
            }
        })
    }
}
