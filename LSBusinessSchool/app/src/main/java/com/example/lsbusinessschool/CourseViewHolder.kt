package com.example.lsbusinessschool

import androidx.recyclerview.widget.RecyclerView
import com.example.lsbusinessschool.databinding.RowCourseBinding
import com.example.lsbusinessschool.model.CourseModel

class CourseViewHolder(private val bind: RowCourseBinding, private val listener: OnCourseListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(course: CourseModel) {
        bind.tvCourseYear.text = "${course.courseYear}"
        bind.tvCourseDesignation.text = "${course.courseDesignation}"
        bind.tvCourseDesignation.setOnClickListener {
            listener.OnClick(course)
        }

        bind.tvCourseYear.setOnClickListener {
            listener.OnClick(course)
        }
    }
}