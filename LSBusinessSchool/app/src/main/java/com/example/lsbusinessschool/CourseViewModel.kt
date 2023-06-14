package com.example.lsbusinessschool

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lsbusinessschool.database.StudentRepository
import com.example.lsbusinessschool.model.CourseModel

class CourseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = StudentRepository(application.applicationContext)

    private val _courses = MutableLiveData<List<CourseModel>>()
    val courses: LiveData<List<CourseModel>> get() = _courses

    fun getAllCourses() {
        _courses.value = repository.getAllCourses()
    }

    fun getCourse(courseId: Int): LiveData<CourseModel?> {
        val course = repository.getCourse(courseId)
        return MutableLiveData<CourseModel?>(course)
    }
}