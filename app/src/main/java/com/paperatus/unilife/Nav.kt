package com.paperatus.unilife

import android.content.Context
import kotlin.math.sqrt

data class LatLong(val latitude: Double, val longitude: Double)

object Nav {

    val places = hashMapOf (
        "Administration" to LatLong(51.0781, -114.1272),
        "Biological Sciences" to LatLong(51.0799, -114.1256),
        "Earth Sciences" to LatLong(51.0803, -114.1291),
        "Education Block" to LatLong(51.0767, -114.1265),
        "Education Tower" to LatLong(51.0770, -114.1261),
        "EEEL" to LatLong(51.0811, -114.1295),
        "Engineering Block C" to LatLong(51.0801, -114.1314),
        "Engineering Block B" to LatLong(51.0807, -114.1315),
        "Engineering Block C" to LatLong(51.0810, -114.1318),
        "Engineering Block D" to LatLong(51.0805, -114.1326),
        "Engineering Block E" to LatLong(51.0798, -114.1325),
        "Engineering Block F" to LatLong(51.0794, -114.1325),
        "Engineering Block G" to LatLong(51.0802, -114.1320),
        "ICT" to LatLong(51.0802, -114.1303),
        "Kinesiology A" to LatLong(51.0777, -114.1333),
        "Kinesiology B" to LatLong(51.0769, -114.1325),
        "MacEwan Hall" to LatLong(51.0783, -114.1314),
        "Mathematical Sciences" to LatLong(51.0799, -114.1279),
        "Murray Fraser Hall" to LatLong(51.0770, -114.1285),
        "Olympic Oval" to LatLong(51.0770, -114.1357),
        "Professional Faculties" to LatLong(51.0773, -114.1271),
        "Reeve Theatre" to LatLong(51.0762, -114.1308),
        "Rozsa Centre" to LatLong(51.0764, -114.1314),
        "Science A" to LatLong(51.0794, -114.1277),
        "Science B" to LatLong(51.0794, -114.1297),
        "Science Theatres" to LatLong(51.0797, -114.1272),
        "Scurfield Hall" to LatLong(51.0774, -114.1247),
        "Social Sciences" to LatLong(51.0791, -114.1269),
        "TFDL" to LatLong(51.0775, -114.1299),
        "University Theatre" to LatLong(51.0770, -114.1302)
    )
}