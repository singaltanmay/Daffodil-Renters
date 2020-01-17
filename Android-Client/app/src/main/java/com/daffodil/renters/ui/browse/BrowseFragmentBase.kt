package com.daffodil.renters.ui.browse


import android.util.Log
import androidx.fragment.app.Fragment
import com.daffodil.renters.api.RetrofitClient
import com.daffodil.renters.model.House
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class BrowseFragmentBase : Fragment() {

    var childInteraction: ChildFragmentInteraction? = null

    fun loadAllHouses() {

        val call = RetrofitClient.getInstance().getAPIClient().getAllHouses()
        call.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                val houses = response.body()
                childInteraction?.onDataLoaded(houses)
                Logv("Call successful. Items received: " + houses?.size)
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                Logv("Call failed to server ${t.message}")
            }
        })
    }

    interface ChildFragmentInteraction {
        fun onDataLoaded(houses: List<House>?)
    }

    override fun onStart() {
        super.onStart()
        loadAllHouses()
    }

    fun Logv(message: String) = Log.v(BrowseFragmentBase::class.java.simpleName, message)

}
