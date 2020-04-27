package com.paperatus.unilife.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.paperatus.unilife.CourseData
import com.paperatus.unilife.R
import kotlinx.android.synthetic.main.fragment_home_chat.view.*

class ChatFragment(val data: CourseData) : Fragment() {
    val db = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.title.text = "${data.name} ${data.number}"

        db.collection("${data.name}${data.number}")
            .addSnapshotListener { value, e ->
                if (e != null) {
                    Log.d("Firebase", "Error: ${e.message}")
                    return@addSnapshotListener
                }

                val map = HashMap<Long, QueryDocumentSnapshot>()
                for (v in value!!) {
                    map[v["timestamp"] as Long] = v

                }

                view.chat.text = ""
                map.keys.sorted().forEach {
                    val v = map[it]!!
                    val msg = v["message"] as String
                    val sender = v["name"]
                    view.chat.text = "${view.chat.text}\n$sender: $msg"
                }
            }

        view.send.setOnClickListener {
            val msg = hashMapOf(
                "name" to "User1",
                "message" to view.chat_input.text.toString(),
                "timestamp" to System.currentTimeMillis() / 1000
            )

            db.collection("${data.name}${data.number}")
                .add(msg)
                .addOnSuccessListener {
                    Log.d("Firebase", "Sent message")
                }
                .addOnFailureListener {
                    Log.d("Firebase", "Cannot send message: ${it.stackTrace}")
                }


            view.chat_input.text.clear()
        }
    }
}