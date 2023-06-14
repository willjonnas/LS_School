package com.example.lsbusinessschool

import androidx.recyclerview.widget.RecyclerView
import com.example.lsbusinessschool.databinding.RowStudentBinding
import com.example.lsbusinessschool.model.CourseModel
import com.example.lsbusinessschool.model.StudentModel

class StudentViewHolder(
    private val bind: RowStudentBinding,
    private val listener: OnStudentListener?
) : RecyclerView.ViewHolder(bind.root) {

    fun bind(student: StudentModel, course: List<CourseModel>) {

        bind.tvStudentName.text = student.nameStudent
        bind.tvStudentEmail.text = student.emailStudent

        bind.tvStudentName.setOnClickListener {
            listener?.OnClick(student)
        }

        bind.tvStudentEmail.setOnClickListener {
            listener?.OnClick(student)
        }
    }
}