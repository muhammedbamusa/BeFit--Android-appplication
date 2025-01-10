package com.example.befit
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class AddExerciseActivity : AppCompatActivity() {
    private lateinit var exerciseListView: ListView
    private lateinit var searchBar: EditText
    private val allExercises = listOf("Bench Press", "Squat", "Deadlift", "Pull-Ups", "Push-Up", "Lunges")
    private val filteredExercises = mutableListOf<String>() // Mutable list for filtered results
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercise)

        exerciseListView = findViewById(R.id.exerciseListView)
        searchBar = findViewById(R.id.searchBar)

        // Initialize the filtered list with all exercises
        filteredExercises.addAll(allExercises)

        // Adapter for displaying exercises
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, filteredExercises)
        exerciseListView.adapter = adapter

        // Setup search bar functionality
        setupSearchBar()

        // Handle exercise selection
        setupExerciseSelection()
    }
    private fun setupSearchBar() {
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(charSequence: CharSequence?, start: Int, before: Int, count: Int) {
                val query = charSequence.toString()
                filterExercises(query)
            }
            override fun afterTextChanged(editable: Editable?) {} }) }
    private fun filterExercises(query: String) {
        filteredExercises.clear()
        filteredExercises.addAll(allExercises.filter { it.contains(query, ignoreCase = true) })
        adapter.notifyDataSetChanged()
        if (filteredExercises.isEmpty()) {
            Toast.makeText(this, "No exercises found", Toast.LENGTH_SHORT).show()
        }
    }
    private fun setupExerciseSelection() {
        exerciseListView.setOnItemClickListener { _, _, position, _ ->
            val selectedExercise = filteredExercises[position] // Get the selected exercise
            val resultIntent = Intent().apply {
                putExtra("EXERCISE_NAME", selectedExercise)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            Toast.makeText(this, "Selected Exercise: $selectedExercise", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
