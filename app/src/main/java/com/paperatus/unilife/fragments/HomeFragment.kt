package com.paperatus.unilife.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paperatus.unilife.R
import kotlinx.android.synthetic.main.fragment_home_main.*
import kotlinx.android.synthetic.main.fragment_home_main.view.*

class HomeFragment : Fragment() {

    var main = HomeMainFragment()
    var courses = HomeCoursesFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().add(R.id.container_home, main).addToBackStack(null).commit()
    }

    fun back() {
        if (childFragmentManager.backStackEntryCount > 1)
            childFragmentManager.popBackStack()
    }


}