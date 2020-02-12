package com.daffodil.renters.ui.listing


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.daffodil.renters.R
import com.daffodil.renters.api.RetrofitClient
import com.daffodil.renters.model.ListingSkeletal
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

    private fun refreshViewsWithData(listing: ListingSkeletal) {

        val bhkString = "${listing.bedrooms} ${listing.propertyType}"

        Logv(bhkString)

        listing_bhk_textview.text = bhkString

    }

    private fun loadProperty(id: Long) {

        val call = RetrofitClient.getInstance().getAPIClient().getListings()
        call.enqueue(object : Callback<List<ListingSkeletal>> {
            override fun onResponse(
                call: Call<List<ListingSkeletal>>,
                response: Response<List<ListingSkeletal>>
            ) {
                val listings = response.body()
                if ((listings != null && listings.size > 0)) {
                    refreshViewsWithData(listings.get(0))
                }
                Logv("Call successful. Items received: " + listings?.size)
            }

            override fun onFailure(call: Call<List<ListingSkeletal>>, t: Throwable) {
                Logv("Call failed to server ${t.message}")
            }
        })

    }


    fun Logv(message: String) = Log.v(ListingFragment::class.java.simpleName, message)


}
