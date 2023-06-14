package com.example.lsbusinessschool

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lsbusinessschool.databinding.ActivityStudentListBinding
import com.example.lsbusinessschool.model.CourseModel
import com.example.lsbusinessschool.model.StudentModel

class StudentListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentListBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var courseViewModel: CourseViewModel
    private val adapter = StudentAdapter()

    private var selectedCourse: CourseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the ViewModel
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        // Set up the RecyclerView with the adapter and the layout manager
        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        // Set up the listener for when a student is clicked
        val listener = object : OnStudentListener {
            override fun OnClick(student: StudentModel) {
                val intent = Intent()
                intent.putExtra(EXTRA_STUDENT_ID, student.id)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
        adapter.attachListener(listener)

        // Observe the students LiveData and update the adapter when the list of students changes
        viewModel.students.observe(this) { students ->
            students?.let {
                adapter.updateStudents(it)
            }
        }

        // Load all students from the database
        //  viewModel.getAllStudents()

        // Observe the courses LiveData and update the adapter when the list of courses changes
        courseViewModel.courses.observe(this) { courses ->
            courses?.let {
                adapter.updateCourses(it)
                binding.spChooseCourse.adapter = object : ArrayAdapter<CourseModel>(this, 0, it) {
                    override fun getView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        val view = convertView ?: LayoutInflater.from(context)
                            .inflate(R.layout.spinner_item_course, parent, false)
                        val course = getItem(position)
                        val courseYearTextView =
                            view.findViewById<TextView>(R.id.spinner_item_course_year)
                        val courseDesignationTextView =
                            view.findViewById<TextView>(R.id.spinner_item_course_designation)
                        courseYearTextView.text = course?.courseYear.toString()
                        courseDesignationTextView.text = course?.courseDesignation
                        return view
                    }

                    override fun getDropDownView(
                        position: Int,
                        convertView: View?,
                        parent: ViewGroup
                    ): View {
                        return getView(position, convertView, parent)
                    }
                }

                binding.spChooseCourse.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            selectedCourse =
                                if (position >= 0 && position < it.size) it[position] else null
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            selectedCourse = null
                        }
                    }
            }
        }


        // Load all courses from the database
        courseViewModel.getAllCourses()

        binding.btnSave.setOnClickListener {
            if (selectedCourse == null) {
                Toast.makeText(this, "Please select a course", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nameStudent = binding.etStudentName.text.toString()
            val addressStudent = binding.etAddressStudent.text.toString()
            val emailStudent = binding.etEmailStudent.text.toString()
            val ageStudent = binding.etAgeStudent.text.toString().toIntOrNull()
            val phoneStudent = binding.etPhoneStudent.text.toString().toIntOrNull()

            if (nameStudent.isNotEmpty() && emailStudent.isNotEmpty() && ageStudent != null && phoneStudent != null) {
                val student = StudentModel(
                    0,
                    selectedCourse!!.courseId,
                    nameStudent,
                    addressStudent,
                    emailStudent,
                    ageStudent,
                    phoneStudent
                )
                viewModel.insertStudent(
                    nameStudent,
                    addressStudent,
                    emailStudent,
                    ageStudent,
                    phoneStudent,
                    selectedCourse!!.courseId
                )
                binding.etStudentName.text.clear()
                binding.etAddressStudent.text.clear()
                binding.etEmailStudent.text.clear()
                binding.etAgeStudent.text.clear()
                binding.etPhoneStudent.text.clear()
                startActivity(Intent(this, StudentMainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, StudentMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        const val EXTRA_STUDENT_ID = "extra_student_id"
    }
}