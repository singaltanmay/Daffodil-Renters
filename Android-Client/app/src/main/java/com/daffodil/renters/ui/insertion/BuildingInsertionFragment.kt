package com.daffodil.renters.ui.insertion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daffodil.renters.R
import kotlinx.android.synthetic.main.fragment_building_insertion.*

class BuildingInsertionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_building_insertion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        building_creation_next_button.setOnClickListener {
            findNavController().navigate(R.id.action_buildingCreationFragment_to_parkingSpotInsertionFragment)
        }
    }
}
