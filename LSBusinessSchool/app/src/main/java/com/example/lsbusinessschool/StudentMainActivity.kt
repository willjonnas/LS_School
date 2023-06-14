package com.example.lsbusinessschool

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lsbusinessschool.databinding.ActivityStudentMainBinding
import com.example.lsbusinessschool.model.StudentModel

class StudentMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudentMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = StudentAdapter()
    private var selectedStudent: StudentModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentMainBinding.inflate(layoutInflater)
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

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, StudentListActivity::class.java))
        }

        binding.btnEdit.setOnClickListener {
            selectedStudent?.let { student ->
                val intent = Intent(this, StudentEditActivity::class.java).apply {
                    putExtra("STUDENT_ID", student.id)
                }
                startActivity(intent)
            } ?: run {
                Toast.makeText(applicationContext, "Please select a student", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnDelete.setOnClickListener {
            selectedStudent?.let { student ->
                viewModel.deleteStudent(student.id)
                viewModel.getAllStudents()
            } ?: run {
                Toast.makeText(applicationContext, "Please select a student", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.btnGoBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnDetails.setOnClickListener {
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

        binding.btnOrderAsc.setOnClickListener {
            viewModel.getAllStudentByNameAsc()
        }

        binding.btnOrderDesc.setOnClickListener {
            viewModel.getAllStudentByNameDesc()
        }

        observeViewModel()
        viewModel.getAllStudents()
    }

    private fun observeViewModel() {
        viewModel.students.observe(this) { students ->
            adapter.updateStudents(students)
        }
    }
}