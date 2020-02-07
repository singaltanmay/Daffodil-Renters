package com.daffodil.renters.ui.user.creation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daffodil.renters.R

class UserCreationFragment : Fragment() {

    companion object {
        fun newInstance() = UserCreationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_creation_fragment, container, false)
    }

}
