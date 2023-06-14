package com.example.lsbusinessschool

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lsbusinessschool.databinding.RowCourseBinding
import com.example.lsbusinessschool.model.CourseModel

class CourseAdapter : RecyclerView.Adapter<CourseViewHolder>() {

    private var courseList: List<CourseModel> = listOf()
    private lateinit var listener: OnCourseListener

    fun attachListener(listener: OnCourseListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val item = RowCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CourseViewHolder(item, listener)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courseList[position])
    }

    override fun getItemCount() = courseList.size

    fun updateCourses(courses: List<CourseModel>) {
        this.courseList = courses
        notifyDataSetChanged()
    }
}