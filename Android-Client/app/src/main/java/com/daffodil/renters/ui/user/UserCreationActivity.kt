package com.daffodil.renters.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.daffodil.renters.R
import com.daffodil.renters.ui.NavigationHost

class UserCreationActivity : AppCompatActivity(), UserLoginFragment.OnUserLoginListener, NavigationHost {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_creation_activity)
        if (savedInstanceState == null) {
            navigateTo(UserLoginFragment())
        }
    }

    override fun OnLogin(userId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * Navigate to the given fragment.
     *
     * @param fragment       Fragment to navigate to.
     * @param addToBackstack Whether or not the current fragment should be added to the backstack.
     */
    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}
