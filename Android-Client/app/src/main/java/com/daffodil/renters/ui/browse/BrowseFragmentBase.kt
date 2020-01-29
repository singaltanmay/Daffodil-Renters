package com.daffodil.renters.ui.browse


import android.util.Log
import androidx.fragment.app.Fragment
import com.daffodil.renters.api.RetrofitClient
import com.daffodil.renters.model.ListingSkeletal
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BrowseFragmentBase : Fragment() {

    var childInteraction: ChildFragmentInteraction? = null

    fun loadAllListingSkeletals() {

        val call = RetrofitClient.getInstance().getAPIClient().getAllListingSkeletals()
        call.enqueue(object : Callback<List<ListingSkeletal>> {
            override fun onResponse(call: Call<List<ListingSkeletal>>, response: Response<List<ListingSkeletal>>) {
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
        fun onDataLoaded(houses: List<ListingSkeletal>?)
    }

    override fun onStart() {
        super.onStart()
        loadAllListingSkeletals()
    }

    fun Logv(message: String) = Log.v(BrowseFragmentBase::class.java.simpleName, message)

}
