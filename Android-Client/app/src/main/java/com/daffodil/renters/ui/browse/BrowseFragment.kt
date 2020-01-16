package com.daffodil.renters.ui.browse


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daffodil.renters.R
import com.daffodil.renters.api.RetrofitClient
import com.daffodil.renters.api.getData

class BrowseFragment : Fragment() {

    private lateinit var parentView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentView = inflater.inflate(R.layout.fragment_browse, container, false)

        initRecyclerView()


        val retrofitClient = RetrofitClient.getInstance()
        val apiClient = retrofitClient?.getAPIClient()
        val call = apiClient?.getAllHouses()


        return parentView
    }

    private fun initRecyclerView() {
        val recyclerView = parentView.findViewById<RecyclerView>(R.id.browse_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = BrowseItemAdapter(getData().toList())
    }


}
