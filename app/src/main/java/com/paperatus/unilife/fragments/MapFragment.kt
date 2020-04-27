package com.paperatus.unilife.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle
import com.paperatus.unilife.LatLong
import com.paperatus.unilife.MainActivity
import com.paperatus.unilife.Nav
import com.paperatus.unilife.R
import kotlinx.android.synthetic.main.fragment_map.view.*
import org.json.JSONObject


class MapFragment(var activity: MainActivity) : Fragment() {

    lateinit var mapView: MapView
    lateinit var gMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_map, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.m_map_view

        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync {
            gMap = it
            gMap.isMyLocationEnabled = true
            gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(51.077565, -114.129045), 15.0f))

            drawBuildings()
        }

        view.fab.setOnClickListener {
            val s = Spinner(context)
            s.adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_dropdown_item, Nav.places.keys.toList().sorted())
            AlertDialog.Builder(context)
                .setTitle("Where would you like to go?")
                .setPositiveButton("Go!") { dialog, which ->
                    if (activity.recentLocation == null) {
                        Toast.makeText(context, "Waiting for location.", Toast.LENGTH_SHORT).show()
                        return@setPositiveButton
                    }

                    drawLine(LatLong(activity.recentLocation!!.latitude, activity.recentLocation!!.longitude), Nav.places[s.selectedItem]!!)
                }
                .setView(s)
                .setNegativeButton("Cancel") { dialog, which ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    fun drawBuildings() {
        val l = GeoJsonLayer(gMap, JSONObject(context!!.assets.open("buildings.geojson").bufferedReader().readText()))
        l.defaultPolygonStyle.also {
            it.fillColor = 0x7720964f.toInt()
            it.strokeColor = 0x77124d29
            it.strokeWidth = 3.0f
        }
        l.addLayerToMap()
    }

    var line: Polyline? = null;
    fun drawLine(start: LatLong, end: LatLong) {
        line?.remove();
        val o = PolylineOptions()
        o.width(5f)
        o.color(0xFFFF0000.toInt())
        o.add(LatLng(start.latitude, start.longitude))
        o.add(LatLng(end.latitude, end.longitude));
        line = gMap.addPolyline(o);
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()

    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }
}