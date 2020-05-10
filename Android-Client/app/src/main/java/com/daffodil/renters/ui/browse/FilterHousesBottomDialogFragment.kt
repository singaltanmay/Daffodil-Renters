package com.daffodil.renters.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.daffodil.renters.R
import com.daffodil.renters.application.RentersApplication
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButtonToggleGroup
import kotlinx.android.synthetic.main.filter_listings_bottom_sheet_dialog_layout.*

class FilterHousesBottomDialogFragment(val childFragmentInteraction: ControllerFragment.ChildFragmentInteraction?) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.filter_listings_bottom_sheet_dialog_layout,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val materialButtonToggleGroup =
            sort_sheet_sort_options_button_toggle_group as MaterialButtonToggleGroup
        materialButtonToggleGroup.addOnButtonCheckedListener { group, checkedId, isChecked ->
            when (checkedId) {
                R.id.sort_sheet_pincode_button -> {
                    childFragmentInteraction?.sortDataPinCode(isChecked)
                }
            }
        }

    }
}