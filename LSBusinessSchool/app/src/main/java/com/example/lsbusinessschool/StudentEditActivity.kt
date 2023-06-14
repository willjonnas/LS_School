package com.example.lsbusinessschool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lsbusinessschool.databinding.ActivityStudentEditBinding
import com.example.lsbusinessschool.model.CourseModel
import com.example.lsbusinessschool.model.StudentModel

class StudentEditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentEditBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()
    private lateinit var courseViewModel: CourseViewModel
    private var selectedCourse: CourseModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        courseViewModel = ViewModelProvider(this).get(CourseViewModel::class.java)

        // Set up the RecyclerView
        binding.recyclerStudents.layoutManager = LinearLayoutManager(this)
        binding.recyclerStudents.adapter = adapter

        // Observe the students LiveData and update the adapter when the list of students changes
        viewModel.students.observe(this) { students ->
            students?.let {
                adapter.updateStudents(it)
            }
        }

        // Load all students from the database
        //  viewModel.getAllStudents()

        // Get the student ID from the intent
        val id = intent.getIntExtra("STUDENT_ID", 0)

        // Find the views in the layout
        val tvId: TextView = binding.tvIdStudent
        val studentName: EditText = binding.etStudentName
        val studentAddress: EditText = binding.etAddressStudent
        val studentEmail: EditText = binding.etEmailStudent
        val studentAge: EditText = binding.etAgeStudent
        val studentPhone: EditText = binding.etPhoneStudent
        val spinnerCourses: Spinner = binding.spChooseCourse

        viewModel.getStudent(id).observe(this) { student ->
            student?.let {
                tvId.text = student.id.toString()
                studentName.setText(student.nameStudent)
                studentAddress.setText(student.addressStudent)
                studentEmail.setText(student.emailStudent)
                studentAge.setText(student.ageStudent.toString())
                studentPhone.setText(student.phoneStudent.toString())
                courseViewModel.getCourse(student.courseId).observe(this) { course ->
                    course?.let {
                        selectedCourse = course
                        spinnerCourses.setSelection(
                            getSpinnerIndex(
                                courseViewModel.courses.value,
                                course
                            )
                        )
                    }
                }
            }
        }

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

        // Set up the course spinner
        viewModel.courses.observe(this, Observer<List<CourseModel>> { courses ->
            val courseNames = courses.map { it.courseDesignation }
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courseNames)
            spinnerCourses.adapter = adapter

            // Get the student from the view model
            viewModel.getStudent(id).observe(this, Observer<StudentModel?> { student ->
                tvId.text = "Student ${student?.id}"
                studentName.setText(student?.nameStudent)
                studentAddress.setText(student?.addressStudent)
                studentEmail.setText(student?.emailStudent)
                studentAge.setText(student?.ageStudent.toString())
                studentPhone.setText(student?.phoneStudent.toString())

                // Set the selected course
                student?.courseId?.let { courseId ->
                    val index = courses.indexOfLast { it.courseId == courseId }
                    if (index >= 0) {
                        spinnerCourses.setSelection(index)
                        selectedCourse = courses[index]
                    }
                }
            })
        })

        // Set up the save button
        binding.btnSave.setOnClickListener {
            val student = StudentModel(
                id, // pass the id to the updated student
                selectedCourse?.courseId ?: 0, // use the selected course, or 0 if none selected
                studentName.text.toString(),
                studentAddress.text.toString(),
                studentEmail.text.toString(),
                studentAge.text.toString().toInt(),
                studentPhone.text.toString().toInt()
            )

            viewModel.updateStudent(student)

            // Set the result and finish the activity
            setResult(RESULT_OK, Intent().apply {
                putExtra("STUDENT_ID", id)
            })
            val intent = Intent(this, StudentMainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, StudentMainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getSpinnerIndex(courses: List<CourseModel>?, course: CourseModel?): Int {
        if (courses == null || course == null) {
            return -1
        }
        for (i in courses.indices) {
            if (courses[i].courseId == course.courseId) {
                return i
            }
        }
        return -1
    }
}