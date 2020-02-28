package com.daffodil.renters.ui.listing


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daffodil.renters.R
import com.daffodil.renters.api.RetrofitClient
import com.daffodil.renters.model.Listing
import kotlinx.android.synthetic.main.fragment_listing.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ListingFragment : Fragment() {

    companion object {
        val PROPERTY_ID_KEY = "propertyId"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_listing, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val propertyId: Long = arguments?.getLong(PROPERTY_ID_KEY, -1) ?: -1

        Toast.makeText(context, propertyId.toString(), Toast.LENGTH_SHORT).show()

        Thread { loadProperty(propertyId) }.start()

    }

    private fun refreshViewsWithData(listing: Listing) {

        val strings = listing.FormattedStrings()

        listing_bhk_textview.text = strings.getHouseSizeAndType()
        listing_house_num_textview.text = listing.addressBuildingName
        listing_distance_textview.text = strings.getRoundedDistanceWithUnit()
        listing_furnishing_type_textview.text = strings.getFurnishingTypeSentenceCase()
        listing_area_textview.text = strings.getArea()
        listing_rent_textview.text = strings.getRentPerMonth()

        listing_project_name_textview.text = listing.addressLocalityName
        listing_bedrooms_num_textview.text = listing.bedrooms.toString()
        listing_description_text_view.text = listing.description

        listing_seller_name_textview.text = strings.getSellerFullNameSentenceCase()
        listing_seller_type_textview.text = strings.getSellerTypeSentenceCase()

        listing_view_on_map_button.setOnClickListener {
            findNavController().navigate(
                R.id.action_listingFragment_to_mapListingFragment,
                bundleOf(
                    MapListingFragment.KEY_LATITUDE to listing.latitude,
                    MapListingFragment.KEY_LONGITUDE to listing.longitude,
                    MapListingFragment.KEY_MARKER_TITLE to strings.getFullAddress()
                )
            )
        }

        listing_seller_contact_material_button.setOnClickListener {
            val phoneNumber = listing.seller?.phoneNumber
            if (!phoneNumber.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null))
                startActivity(intent)
            }
        }

        val lineCount = listing_description_text_view.lineCount
        if (lineCount > 4) {
            description_length_toggle_button.setOnClickListener {
                listing_description_text_view.maxLines = lineCount
                (it as Button).text = "Read Less"
                it.setOnClickListener {
                    listing_description_text_view.maxLines = 4
                    (it as Button).text = "Read More"
                }
            }
            description_length_toggle_button.visibility = View.VISIBLE
        }

    }

    private fun loadProperty(id: Long) {

        val call = RetrofitClient.getInstance().getAPIClient().getListings()
        call.enqueue(object : Callback<List<Listing>> {
            override fun onResponse(
                call: Call<List<Listing>>,
                response: Response<List<Listing>>
            ) {
                val listings = response.body()
                if ((listings != null && listings.isNotEmpty())) {
                    refreshViewsWithData(listings[0])
                }
                Logv("Call successful. Items received: " + listings?.size)
            }

            override fun onFailure(call: Call<List<Listing>>, t: Throwable) {
                Logv("Call failed to server ${t.message}")
            }
        })

    }

    fun Logv(message: String) = Log.v(ListingFragment::class.java.simpleName, message)

}
