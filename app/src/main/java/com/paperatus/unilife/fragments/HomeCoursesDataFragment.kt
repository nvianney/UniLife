package com.paperatus.unilife.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paperatus.unilife.CourseData
import com.paperatus.unilife.Courses
import com.paperatus.unilife.R
import kotlinx.android.synthetic.main.course_expand.view.*
import kotlinx.android.synthetic.main.course_root.view.*
import kotlinx.android.synthetic.main.course_root.view.course_title
import kotlinx.android.synthetic.main.fragment_home_courses.*
import kotlinx.android.synthetic.main.fragment_home_courses.view.*

class HomeCoursesDataFragment(val courseName: String) : Fragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_courses, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        view.courses_recycler.layoutManager = llm
        view.courses_recycler.adapter = Adapter(courseName)
    }

    inner class Adapter(val courseName: String) : RecyclerView.Adapter<Adapter.CourseViewHolder>() {

        val courseData = Courses.data[courseName]!!


        inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView
            val description: TextView
            val fav: ImageButton
            val chat: ImageButton

            var data: CourseData? = null

            init {
                title = itemView.course_title
                description = itemView.course_text
                fav = itemView.fav_btn
                chat = itemView.chat_btn

                fav.setOnClickListener {
                    Courses.favourite(context!!, data!!)
                    fav.setImageResource(if (data!! in Courses.favourites) R.drawable.fav else R.drawable.fav_empty)
                }

                chat.setOnClickListener { a ->
                    fragmentManager!!.beginTransaction().replace(R.id.container_home, ChatFragment(data!!)).addToBackStack(null).commit()
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
            val v: View =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.course_expand, parent, false)
            v.setOnClickListener {
                it.consts.visibility = when(it.consts.visibility) {
                    View.GONE -> View.VISIBLE
                    else -> View.GONE
                }
            }
            return CourseViewHolder(v)
        }

        override fun getItemCount(): Int {
            return courseData.size
        }

        override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
            val d = courseData[position]
            holder.title.text = "${d.name} ${d.number}"
            holder.description.text = "${d.summary}\n\n${d.desc}"
            holder.itemView.consts.visibility = View.GONE
            holder.data = d
            holder.fav.setImageResource(if (d in Courses.favourites) R.drawable.fav else R.drawable.fav_empty)
        }


    }
}