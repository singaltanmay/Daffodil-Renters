package com.daffodil.renters.ui.user.creation

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.daffodil.renters.R
import com.daffodil.renters.model.postables.User
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.user_creation_fragment.*

class UserCreationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.user_creation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {

        frag_user_creation_cancel_button.setOnClickListener {
            findNavController().navigateUp()
        }

        frag_user_creation_done_button.setOnClickListener {
            createUser()
        }

    }


    /**
     * Create the user by fetching data from views.
     */
    private fun createUser() {

        if (isFormInputsValid()) {
            val user = User()
            user.firstName = firstNameTextInputEditText.text.toString()
            user.lastName = lastNameTextInputEditText.text.toString()
            user.phoneNumber = phoneNumberTextInputEditText.text.toString()
            user.email = emailIdTextInputEditText.text.toString()
            user.password = passwordTextInputEditText.text.toString()

            // TODO save this bundle containing user credentials
            user.bundle

            //TODO store chip selection somewhere
        }

    }

    private fun isFormInputsValid(): Boolean {
        val b = !firstNameTextInputLayout.enableClearableError(firstNameTextInputEditText)
        val b1 = !lastNameTextInputLayout.enableClearableError(lastNameTextInputEditText)
        val b2 = !emailIdTextInputLayout.enableClearableError(emailIdTextInputEditText)
        val b3 = !phoneNumberTextInputLayout.enableClearableError(phoneNumberTextInputEditText)
        val b4 = isPasswordValid()

        return (b && b1 && b2 && b3 && b4)
    }

    /**
     * Checks if the entered password is valid.
     */
    private fun isPasswordValid(): Boolean {
        val password = passwordTextInputEditText.text.toString()

        if (passwordTextInputLayout.enableClearableError(passwordTextInputEditText)) {
            return false
        }

        if (password.length < 8) {
            passwordTextInputLayout.error = "Password must be at least 8 characters long."
        }

        return true
    }

    /**
     * Extension function to show a given error when the field is empty.
     * If error String is not given it will just complain about field being empty.
     * @return Returns false if the child edit text received an empty string.
     */
    private fun TextInputLayout.enableClearableError(
        child: TextInputEditText,
        errorString: String? = null
    ): Boolean {

        var childInputEmpty = false

        if (errorString.isNullOrEmpty() && child.isEmpty()) {
            this.error = "Field cannot be empty."
            childInputEmpty = true
        } else this.error = errorString

        child.setOnKeyListener { _, _, _ ->
            this.error = null
            false
        }

        return childInputEmpty

    }

    /**
     * Extension function that checks if the input text is null or of length 0.
     */
    private fun TextInputEditText.isEmpty(): Boolean {
        return TextUtils.isEmpty(this.text.toString())
    }

}
