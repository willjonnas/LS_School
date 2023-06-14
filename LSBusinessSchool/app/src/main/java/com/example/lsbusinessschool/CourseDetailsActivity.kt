package com.example.lsbusinessschool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lsbusinessschool.databinding.ActivityCourseDetailsBinding
import com.example.lsbusinessschool.model.CourseModel
import com.example.lsbusinessschool.model.StudentModel

class CourseDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseDetailsBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()
    private var course: CourseModel? = null
    private var selectedStudent: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCourseDetailsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        adapter.attachListener(object : OnStudentListener {
            override fun OnClick(student: StudentModel) {
                selectedStudent = student
                val message = "${student.nameStudent}, ${student.id}"
                Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            }
        })

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
        val editCourseYear: TextView = binding.editCourseYear
        val editCourseDesignation: TextView = binding.editCourseDesignation

        // Get the course from the view model
        viewModel.getCourse(courseId).observe(this, Observer<CourseModel?> { course ->
            this.course = course
            tvId.text = "Course ${course?.courseId}"
            editCourseYear.setText(course?.courseYear.toString())
            editCourseDesignation.setText(course?.courseDesignation)
        })

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, CourseMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnOrderAsc.setOnClickListener {
            viewModel.getStudentsByCourseIdAsc(courseId)
        }

        binding.btnOrderDesc.setOnClickListener {
            viewModel.getStudentsByCourseIdDesc(courseId)
        }

        binding.btnInfo.setOnClickListener {
            selectedStudent?.let { student ->
                val intent = Intent(this, StudentDetailsActivity::class.java).apply {
                    putExtra("STUDENT_ID", student.id)
                }
                startActivity(intent)
            } ?: run {
                Toast.makeText(applicationContext, "Please select a student", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        observeViewModel()
        viewModel.getStudentsByCourseIdAsc(courseId)

    }

    private fun observeViewModel() {
        viewModel.students.observe(this) { students ->
            adapter.updateStudents(students)
        }
    }
}