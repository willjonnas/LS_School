package com.example.lsbusinessschool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lsbusinessschool.databinding.ActivityCourseEditBinding
import com.example.lsbusinessschool.model.CourseModel

class CourseEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseEditBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()
    private var course: CourseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.recyclerCourses.layoutManager = LinearLayoutManager(this)
        binding.recyclerCourses.adapter = adapter


        // Observe the courses LiveData and update the adapter when the list of courses changes
        viewModel.courses.observe(this) { courses ->
            courses?.let {
                adapter.updateCourses(it)
            }
        }

        // Load all courses from the database
        viewModel.getAllCourses()

        // Get the course ID from the intent
        val courseId = intent.getIntExtra("COURSE_ID", 0)

        // Find the views in the layout
        val tvId: TextView = binding.tvId
        val editCourseYear: EditText = binding.editCourseYear
        val editCourseDesignation: EditText = binding.editCourseDesignation

        // Get the course from the view model
        viewModel.getCourse(courseId).observe(this, Observer<CourseModel?> { course ->
            this.course = course
            tvId.text = "Course ${course?.courseId}"
            editCourseYear.setText(course?.courseYear.toString())
            editCourseDesignation.setText(course?.courseDesignation)
        })

        // Set up the save button
        binding.btnSave.setOnClickListener {
            // Update the course with the new values
            val newCourse = CourseModel(
                courseId,
                editCourseYear.text.toString().toInt(),
                editCourseDesignation.text.toString()
            )

            // Update the course in the database
            viewModel.updateCourse(newCourse)

            // Show a toast to indicate that the course has been updated
            Toast.makeText(this, "Course updated", Toast.LENGTH_SHORT).show()

            // Finish the activity
            startActivity(Intent(this, CourseMainActivity::class.java))
            finish()
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, CourseMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}