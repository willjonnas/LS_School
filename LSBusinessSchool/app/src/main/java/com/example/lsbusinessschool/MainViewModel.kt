package com.example.lsbusinessschool

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lsbusinessschool.database.StudentRepository
import com.example.lsbusinessschool.model.CourseModel
import com.example.lsbusinessschool.model.StudentModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StudentRepository(application.applicationContext)

    private val _students = MutableLiveData<List<StudentModel>>()
    val students: LiveData<List<StudentModel>> get() = _students

    private val _courses = MutableLiveData<List<CourseModel>>()
    val courses: LiveData<List<CourseModel>> get() = _courses

    private val _student = MutableLiveData<StudentModel>()
    val student: LiveData<StudentModel> get() = _student

    private val _course = MutableLiveData<CourseModel>()
    val course: LiveData<CourseModel> get() = _course

    private val _changes = MutableLiveData<Long>()
    val newChange: LiveData<Long> get() = _changes

    fun getAllStudents() {
        _students.value = repository.getAllStudent()
    }

    fun getAllStudentByNameAsc() {
        _students.value = repository.getAllStudentByNameAsc()
    }

    fun getStudentsByCourseIdAsc(courseId: Int) {
        _students.value = repository.getStudentsByCourseIdAsc(courseId)
    }

    fun getStudentsByCourseIdDesc(courseId: Int) {
        _students.value = repository.getStudentsByCourseIdDesc(courseId)
    }

    fun getAllCourseByNameAsc() {
        _courses.value = repository.getAllCourseByNameAsc()
    }

    fun getAllCourseByNameDesc() {
        _courses.value = repository.getAllCourseByNameDesc()
    }


    fun getAllStudentByNameDesc() {
        _students.value = repository.getAllStudentByNameDesc()
    }

    fun getAllCourses() {
        _courses.value = repository.getAllCourses()
    }

    fun getStudent(id: Int): LiveData<StudentModel?> {
        val student = repository.getStudent(id)
        return MutableLiveData<StudentModel?>(student)
    }

    fun getCourse(courseId: Int): LiveData<CourseModel?> {
        val course = repository.getCourse(courseId)
        return MutableLiveData<CourseModel?>(course)
    }

    fun insertStudent(
        nameStudent: String,
        addressStudent: String,
        emailStudent: String,
        ageStudent: Int,
        phoneStudent: Int,
        courseId: Int
    ) {
        val model = StudentModel(
            nameStudent = nameStudent,
            addressStudent = addressStudent,
            emailStudent = emailStudent,
            ageStudent = ageStudent,
            phoneStudent = phoneStudent,
            courseId = courseId
        )
        _changes.value = repository.insertStudent(model)
    }

    fun updateStudent(student: StudentModel) {
        _changes.value = repository.updateStudent(student)?.toLong()

    }

    fun insertCourse(course: CourseModel) {
        _changes.value = repository.insertCourse(course)
    }

    fun deleteStudent(id: Int) {
        val model = StudentModel(id = id)
        _changes.value = repository.deleteStudent(model)?.toLong()
    }

    fun deleteCourse(courseId: Int) {
        val model = CourseModel(courseId = courseId)
        val numDeleted = repository.deleteCourse(model)?.toLong()
        if (numDeleted != null && numDeleted > 0) {
            val course = courses.value?.find { it.courseId == courseId }
            if (course != null) {
                val message = "Deleted course: ${course.courseDesignation}"
                Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun updateCourse(course: CourseModel) {
        _changes.value = repository.updateCourse(course)?.toLong()

    }


}