package com.daffodil.renters.ui.user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.daffodil.renters.R
import kotlinx.android.synthetic.main.fragment_user_login.frag_user_login_cancel_button as cancelButton
import kotlinx.android.synthetic.main.fragment_user_login.frag_user_login_next_button as loginButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnUserLoginListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_login, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        Thread { setupListeners() }.start()
    }

    private fun setupListeners() {
        cancelButton.setOnClickListener {
            activity?.finish()
        }
        loginButton.setOnClickListener {
            loginButtonPressed()
        }
    }

    private fun loginButtonPressed() {
        //TODO actually attempt login
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnUserLoginListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnUserLoginListener {
        fun OnLogin(userId: Long)
    }

}
