package com.daffodil.renters.ui.browse


import android.util.Log
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daffodil.renters.R
import com.daffodil.renters.api.RetrofitClient
import com.daffodil.renters.model.ListingSkeletal
import com.daffodil.renters.ui.listing.ListingFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class ControllerFragment : Fragment() {

    var childInteraction: ChildFragmentInteraction? = null

    fun loadAllListingSkeletals() {

        val call = RetrofitClient.getInstance().getAPIClient().getListings()
        call.enqueue(object : Callback<List<ListingSkeletal>> {
            override fun onResponse(
                call: Call<List<ListingSkeletal>>,
                response: Response<List<ListingSkeletal>>
            ) {
                val houses = response.body()
                childInteraction?.onDataLoaded(houses)
                Logv("Call successful. Items received: " + houses?.size)
            }

            override fun onFailure(call: Call<List<ListingSkeletal>>, t: Throwable) {
                Logv("Call failed to server ${t.message}")
            }
        })
    }

    interface ChildFragmentInteraction {
        fun onDataLoaded(listings: List<ListingSkeletal>?)
    }

    fun viewListing(propertyId: Long?) {
        findNavController().navigate(
            R.id.action_browseFragment_to_listingFragment,
            bundleOf(ListingFragment.PROPERTY_ID_KEY to propertyId)
        )
    }

    override fun onStart() {
        super.onStart()
        loadAllListingSkeletals()
    }

    fun Logv(message: String) = Log.v(ControllerFragment::class.java.simpleName, message)

}
