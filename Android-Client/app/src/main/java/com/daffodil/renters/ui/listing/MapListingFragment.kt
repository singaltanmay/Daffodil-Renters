package com.daffodil.renters.ui.listing


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daffodil.renters.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapListingFragment : Fragment(), OnMapReadyCallback {

    companion object {
        const val KEY_LATITUDE = "lat"
        const val KEY_LONGITUDE = "lon"
        const val KEY_MARKER_TITLE = "title"
    }

    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.container_fragment, container, false)

        val mapFragment = initMap()
        mapFragment.getMapAsync(this)


        return view
    }


    fun initMap(): SupportMapFragment {

        val supportMapFragment = SupportMapFragment()
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(
                R.id.fragment_container,
                supportMapFragment
            )?.commit()

        return supportMapFragment
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0

        if (arguments == null) return

        val args = arguments!!

        val lat = args.getDouble(KEY_LATITUDE)
        val lon = args.getDouble(KEY_LONGITUDE)
        val title = args.getString(KEY_MARKER_TITLE)

        val latLng = LatLng(lat, lon)

        map?.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
                .alpha(1.56f)
        )
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f))

    }
}
