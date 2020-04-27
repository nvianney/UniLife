package com.paperatus.unilife.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paperatus.unilife.Courses
import com.paperatus.unilife.R
import kotlinx.android.synthetic.main.course_root.view.*
import kotlinx.android.synthetic.main.fragment_home_courses.*
import kotlinx.android.synthetic.main.fragment_home_courses.view.*

class HomeCoursesFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_courses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view.courses_recycler.layoutManager = llm
        view.courses_recycler.adapter = Adapter()
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.CourseViewHolder>() {


        inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView

            init {
                title = itemView.course_title
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
            val v: View =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.course_root, parent, false)
            v.setOnClickListener { this@HomeCoursesFragment.onCardClick(v) }
            return CourseViewHolder(v)
        }

        override fun getItemCount(): Int {
            return Courses.names.size
        }

        override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
            holder.title.text = Courses.names[position]
        }


    }
    fun onCardClick(v: View) {
        val name = ((v as ViewGroup)[0] as TextView).text.toString()
        fragmentManager!!.beginTransaction().replace(R.id.container_home, HomeCoursesDataFragment(name)).addToBackStack(null).commit()
    }
}