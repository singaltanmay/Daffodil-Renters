package com.daffodil.renters.ui.browse


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daffodil.renters.R
import com.daffodil.renters.api.RetrofitClient
import com.daffodil.renters.api.getData
import com.daffodil.renters.model.House
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BrowseFragment : Fragment() {

    private lateinit var parentView: View
    private lateinit var adapter: BrowseItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentView = inflater.inflate(R.layout.fragment_browse, container, false)

        initRecyclerView()

        return parentView
    }

    override fun onStart() {
        super.onStart()
        loadAllHouses()
    }

    private fun initRecyclerView() {
        val recyclerView = parentView.findViewById<RecyclerView>(R.id.browse_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = BrowseItemAdapter(getData().toList())
        recyclerView.adapter = adapter
    }

    fun loadAllHouses() {
        val call = RetrofitClient.getInstance().getAPIClient().getAllHouses()
        call.enqueue(object : Callback<List<House>> {
            override fun onResponse(call: Call<List<House>>, response: Response<List<House>>) {
                val houses = response.body()
                adapter.data = houses
                adapter.notifyDataSetChanged()
                Logv("Call successful. Items received: " + houses?.size)
            }

            override fun onFailure(call: Call<List<House>>, t: Throwable) {
                Logv("Call failed to server ${t.message}")
            }
        })
    }

    fun Logv(message: String) = Log.v(BrowseFragment::class.java.simpleName, message)

}
