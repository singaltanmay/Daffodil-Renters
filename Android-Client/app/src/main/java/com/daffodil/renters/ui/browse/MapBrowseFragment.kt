package com.daffodil.renters.ui.browse


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daffodil.renters.R
import com.daffodil.renters.model.House
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapBrowseFragment : BrowseFragmentBase(), BrowseFragmentBase.ChildFragmentInteraction,
    OnMapReadyCallback {

    private var map: GoogleMap? = null
    private var houses: List<House>? = null

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
        setHouseMarkers()

//        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    fun setHouseMarkers() {
        if (map != null && houses != null) {

            val house = houses?.get(0)
            if (house != null) {
                val latLng = LatLng(
                    house.latitude,
                    house.longitude
                )
                map?.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                map?.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10.0f))

                houses?.forEach {
                    map?.addMarker(
                        MarkerOptions().position(
                            LatLng(
                                it.latitude,
                                it.longitude
                            )
                        ).title(house.address)
                    )
                }
            }
        }
    }

    override fun onDataLoaded(houses: List<House>?) {
        this.houses = houses
        setHouseMarkers()
    }
}
