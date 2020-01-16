package com.daffodil.renters.ui.browse

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daffodil.renters.R

class BrowseItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val parent = itemView

    val addressTextView = parent.findViewById<TextView>(R.id.address_text_view)

}