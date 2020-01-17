package com.daffodil.renters.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daffodil.renters.R
import com.daffodil.renters.model.House

class ListBrowseFragment : BrowseFragmentBase(), BrowseFragmentBase.ChildFragmentInteraction {


    private lateinit var parentView: View
    private lateinit var adapter: BrowseItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.childInteraction = this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        parentView = inflater.inflate(R.layout.recycler_view_layout, container, false)

        initRecyclerView()
        return parentView
    }

    private fun initRecyclerView() {
        val recyclerView = parentView.findViewById<RecyclerView>(R.id.browse_recycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = BrowseItemAdapter(null)
        recyclerView.adapter = adapter
    }

    override fun onDataLoaded(houses: List<House>?) {
        adapter.data = houses
        adapter.notifyDataSetChanged()
    }

    class BrowseItemAdapter(var data: List<House>?) :
        RecyclerView.Adapter<BrowseItemAdapter.BrowseItemViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseItemViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.house_card_item, parent, false)

            return BrowseItemViewHolder(view)
        }

        override fun onBindViewHolder(holder: BrowseItemViewHolder, position: Int) {
            holder.addressTextView.text = data?.get(position)?.address
        }

        override fun getItemCount(): Int = data?.size ?: 0


        class BrowseItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            val parent = itemView

            val addressTextView = parent.findViewById<TextView>(R.id.address_text_view)

        }
    }
}