package com.daffodil.renters.ui.browse

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daffodil.renters.R
import com.daffodil.renters.model.House

class BrowseItemAdapter(var data: List<House>?) :
    RecyclerView.Adapter<BrowseItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.house_card_item, parent, false)

        return BrowseItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: BrowseItemViewHolder, position: Int) {
        holder.addressTextView.text = data?.get(position)?.address
    }

    override fun getItemCount(): Int = data?.size ?: 0
}