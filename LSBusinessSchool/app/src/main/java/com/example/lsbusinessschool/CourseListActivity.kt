package com.example.lsbusinessschool

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lsbusinessschool.databinding.ActivityCourseListBinding
import com.example.lsbusinessschool.model.CourseModel

class CourseListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseListBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Set up the RecyclerView with the adapter and the layout manager
        binding.recyclerCourses.layoutManager = LinearLayoutManager(this)
        binding.recyclerCourses.adapter = adapter

        // Set up the listener for when a course is clicked
        val listener = object : OnCourseListener {
            override fun OnClick(course: CourseModel) {
                val intent = Intent()
                intent.putExtra(EXTRA_COURSE_ID, course.courseId)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
        //adapter.attachListener(listener)

        // Observe the courses LiveData and update the adapter when the list of courses changes
        viewModel.courses.observe(this) { courses ->
            courses?.let {
                adapter.updateCourses(it)
            }
        }

        // Load all courses from the database
        viewModel.getAllCourses()

        binding.btnSave.setOnClickListener {
            val year = binding.editCourseYear.text.toString()
            val designation = binding.editCourseDesignation.text.toString()
            if (year.isNotEmpty() && designation.isNotEmpty()) {
                val course = CourseModel(0, year.toInt(), designation)
                viewModel.insertCourse(course)
                binding.editCourseYear.text.clear()
                binding.editCourseDesignation.text.clear()
                startActivity(Intent(this, CourseMainActivity::class.java))
                finish()
                Toast.makeText(this, "Course added successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, CourseMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_COURSE_ID = "extra_course_id"
    }
}