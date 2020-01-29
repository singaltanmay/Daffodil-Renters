package com.daffodil.renters.ui.browse


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daffodil.renters.R
import com.daffodil.renters.model.ListingSkeletal
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapBrowseFragment : BrowseFragmentBase(), BrowseFragmentBase.ChildFragmentInteraction,
    OnMapReadyCallback {

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
        val latLng = LatLng(
            28.465080, 77.056168
        )
        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12.5f))
        setListingSkeletalMarkers()
    }

    fun setListingSkeletalMarkers() {
        if (map != null && listings != null) {

            var avgLat: Double? = null
            var avgLong: Double? = null

            val size = listings!!.size

            listings?.forEach {
                val latitude = it.latitude
                val longitude = it.longitude
                map?.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                latitude,
                                longitude
                            )
                        ).title(it.address)
                            .alpha(2.34f)
                    )
                if (avgLat == null) avgLat = (latitude / size)
                else avgLat?.plus(latitude / size)
                if (avgLong == null) avgLong = (longitude / size)
                else avgLong?.plus(longitude / size)
                }

            if (avgLat != null && avgLong != null)
                map?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(avgLat!!, avgLong!!)))

        }
    }

    override fun onDataLoaded(listings: List<ListingSkeletal>?) {
        this.listings = listings
        setListingSkeletalMarkers()
    }
}
