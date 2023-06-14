package com.example.lsbusinessschool.dao

import androidx.room.*
import com.example.lsbusinessschool.model.CourseModel
import com.example.lsbusinessschool.model.StudentModel
import com.example.lsbusinessschool.model.StudentWithCourse
import com.example.lsbusinessschool.model.UserModel

@Dao
interface AppDAO {

    // Students

    @Insert
    fun insertStudent(student: StudentModel): Long

    @Update
    fun updateStudent(student: StudentModel): Int

    @Delete
    fun deleteStudent(student: StudentModel): Int

    @Query("SELECT * FROM student_table WHERE id = :id")
    fun getStudent(id: Int): StudentModel

    @Query("SELECT * FROM course_table WHERE courseId = :courseId")
    fun getCourse(courseId: Int): CourseModel

    @Query("SELECT * FROM student_table ORDER BY id DESC")
    fun getStudentData(): List<StudentModel>?

    @Query("SELECT * FROM student_table")
    fun getAllStudent(): List<StudentModel>

    @Query("SELECT * FROM student_table ORDER BY nameStudent ASC")
    fun getAllStudentByNameAsc(): List<StudentModel>

    @Query("SELECT * FROM student_table ORDER BY nameStudent DESC")
    fun getAllStudentByNameDesc(): List<StudentModel>

    @Query("SELECT * FROM course_table ORDER BY courseDesignation ASC")
    fun getAllCourseByNameAsc(): List<CourseModel>

    @Query("SELECT * FROM course_table ORDER BY courseDesignation DESC")
    fun getAllCourseByNameDesc(): List<CourseModel>

    /*@Query("SELECT * FROM student_table INNER JOIN course_table ON student_table.courseId = course_table.courseId WHERE studen_table.courseId = :courseId")
    fun getStudentsByCourse(id: Int): List<StudentWithCourse>?*/

    @Query("SELECT * FROM student_table INNER JOIN course_table ON student_table.courseId = course_table.courseId")
    fun getAllStudentWithCourse(): List<StudentWithCourse>?

    @Query("SELECT * FROM student_table WHERE courseId = :courseId ORDER BY nameStudent ASC")
    fun getStudentsByCourseIdAsc(courseId: Int): List<StudentModel>

    @Query("SELECT * FROM student_table WHERE courseId = :courseId ORDER BY nameStudent DESC")
    fun getStudentsByCourseIdDesc(courseId: Int): List<StudentModel>


    // Courses

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourse(course: CourseModel): Long

    @Query("SELECT * FROM course_table")
    fun getAllCourses(): List<CourseModel>

    @Delete
    fun deleteCourse(course: CourseModel): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateCourse(course: CourseModel): Int

    // User

    @Insert
    fun register(userModel: UserModel): Long

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    fun login(username: String, password: String) : UserModel?

    @Query("SELECT * FROM user_table WHERE username = :username")
    fun getUserByUsername(username: String) : UserModel?

    @Update
    fun refreshToken(userModel: UserModel) : Int

    @Query("SELECT username FROM user_table WHERE username = :username")
    fun getUsername(username: String): String?


}