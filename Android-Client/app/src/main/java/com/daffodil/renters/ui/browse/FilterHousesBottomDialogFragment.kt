package com.daffodil.renters.ui.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.daffodil.renters.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterHousesBottomDialogFragment : BottomSheetDialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.filter_houses_bottom_sheet_dialog_layout,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Do stuff here
    }
}