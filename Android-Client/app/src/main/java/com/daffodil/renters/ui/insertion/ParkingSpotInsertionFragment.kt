package com.daffodil.renters.ui.insertion


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.daffodil.renters.R
import com.daffodil.renters.model.postables.ParkingSpot
import kotlinx.android.synthetic.main.fragment_parking_spot_insertion.*
import kotlinx.android.synthetic.main.recycler_view_layout.*

class ParkingSpotInsertionFragment : Fragment() {

    lateinit var adapter: ParkingSpotRecycler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_parking_spot_insertion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ParkingSpotRecycler(mutableListOf(ParkingSpot()))
        initRecyclerView()
        initFab()
    }

    private fun initRecyclerView() {
        val recyclerView = browse_recycler
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
    }

    private fun initFab() {
        parking_spot_insertion_fab.setOnClickListener {
            adapter.data.add(ParkingSpot())
            adapter.notifyItemInserted(adapter.itemCount - 1)
        }
    }

    inner class ParkingSpotRecycler(val data: MutableList<ParkingSpot>) :
        RecyclerView.Adapter<ParkingSpotRecycler.SpotViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpotViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.parking_spot_item, parent, false)
            return SpotViewHolder(view)
        }

        override fun onBindViewHolder(holder: SpotViewHolder, position: Int) {
            holder.freeParkingCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    holder.chargesEditText.visibility = View.GONE
                } else holder.chargesEditText.visibility = View.VISIBLE
            }
        }

        override fun getItemCount(): Int = data.size

        inner class SpotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val freeParkingCheckBox =
                itemView.findViewById<CheckBox>(R.id.parking_spot_item_free_parking_checkbox)
            val chargesEditText =
                itemView.findViewById<EditText>(R.id.parking_spot_item_monthly_charges_edit_text)
        }
    }

}
