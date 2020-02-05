package com.daffodil.renters.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.daffodil.renters.R

class UserCreationFragment : Fragment() {

    companion object {
        fun newInstance() = UserCreationFragment()
    }

    private lateinit var viewModel: UserCreationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_creation_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UserCreationViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
