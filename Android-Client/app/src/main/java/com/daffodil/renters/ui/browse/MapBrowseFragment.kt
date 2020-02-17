package com.daffodil.renters.ui.browse


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daffodil.renters.R
import com.daffodil.renters.application.RentersApplication
import com.daffodil.renters.model.ListingSkeletal
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MapBrowseFragment : ControllerFragment(), ControllerFragment.ChildFragmentInteraction,
    OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var map: GoogleMap? = null
    private var listings: List<ListingSkeletal>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.childInteraction = this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map_browse, container, false)

        val mapFragment = initMap()
        mapFragment.getMapAsync(this)

        return view
    }

    fun initMap(): SupportMapFragment {

        val supportMapFragment = SupportMapFragment()
        activity?.supportFragmentManager
            ?.beginTransaction()
            ?.add(
                R.id.map_container,
                supportMapFragment
            )?.commit()

        return supportMapFragment
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Coordinates of daffodil software
        val latLng = LatLng(
            28.465082, 77.056162
        )
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.5f))
        setListingSkeletalMarkers()
    }

    fun setListingSkeletalMarkers() {
        if (map != null && listings != null) {

            Thread{map!!.setOnMarkerClickListener(this)}

            val moveToAvgLatLang =
                (context?.applicationContext as RentersApplication).getAppPreferences()
                    .getBoolean("MAP_INIT_AVG_LATLANG", false)

            var avgLat: Double? = null
            var avgLong: Double? = null

            val size = listings!!.size

            listings?.forEach {
                val latitude = it.latitude
                val longitude = it.longitude
                val marker = map?.addMarker(
                    MarkerOptions().position(
                        LatLng(
                            latitude,
                            longitude
                        )
                    ).title(it.address)
                        .alpha(2.34f)
                )
                marker?.tag = it.propertyId
                if (moveToAvgLatLang) {
                    if (avgLat == null) avgLat = (latitude / size)
                    else avgLat?.plus(latitude / size)
                    if (avgLong == null) avgLong = (longitude / size)
                    else avgLong?.plus(longitude / size)
                }
            }

            if (moveToAvgLatLang && avgLat != null && avgLong != null)
                map?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(avgLat!!, avgLong!!)))

        }
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        val id = p0?.tag as Long?
        if (id != null) {
            super.viewListing(id)
            return true
        } else return false
    }

    override fun onDataLoaded(listings: List<ListingSkeletal>?) {
        this.listings = listings
        setListingSkeletalMarkers()
    }
}
