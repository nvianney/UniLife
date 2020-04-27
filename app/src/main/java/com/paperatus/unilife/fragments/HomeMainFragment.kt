package com.paperatus.unilife.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paperatus.unilife.Courses
import com.paperatus.unilife.R
import kotlinx.android.synthetic.main.course_expand.view.*
import kotlinx.android.synthetic.main.fragment_home_main.view.*

class HomeMainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.card_webpage.setOnClickListener {
            val url = "https://www.ucalgary.ca"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        view.card_d2l.setOnClickListener {
            val url = "https://d2l.ucalgary.ca/"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }
        view.card_courses.setOnClickListener {
            showCourses()
        }

        reload()
    }

    fun reload() {
        while (view!!.linear.childCount > 4) {
            view!!.linear.removeViewAt(2)
        }

        Courses.data.values.forEach {
            it.forEach {
                if (it in Courses.favourites) {
                    val v: View =
                        LayoutInflater.from(getContext()).inflate(R.layout.course_expand, view!!.linear, false)
                    v.course_title.text = "${it.name} ${it.number}"
                    v.fav_btn.setImageResource(if (it in Courses.favourites) R.drawable.fav else R.drawable.fav_empty)
                    view!!.linear.addView(v)
                    v.fav_btn.setOnClickListener { a ->
                        Courses.favourite(context!!, it)
                        v.fav_btn.setImageResource(if (it in Courses.favourites) R.drawable.fav else R.drawable.fav_empty)
                    }
                    v.chat_btn.setOnClickListener { a ->
                        fragmentManager!!.beginTransaction().replace(R.id.container_home, ChatFragment(it))
                            .addToBackStack(null).commit()
                    }
                }
            }
        }

        view!!.linear.addView(View(context).also { it.background = null }, ViewGroup.LayoutParams(10, 10))
    }

    fun showCourses() {
        fragmentManager!!.beginTransaction().replace(R.id.container_home, HomeCoursesFragment()).addToBackStack(null)
            .commit()
    }
}