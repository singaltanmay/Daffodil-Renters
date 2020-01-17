package com.daffodil.renters.ui

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.daffodil.renters.R
import com.daffodil.renters.ui.browse.BrowseFragment

class BaseActivity : AppCompatActivity(),
    NavigationHost {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container,
                    BrowseFragment()
                )
                .commit()
        }

        setupBottomNavBar()
    }

    fun setupBottomNavBar() {
        val linearLayout = findViewById<LinearLayout>(R.id.bottom_app_bar_content_container)
        linearLayout.setOnClickListener {
            Toast.makeText(applicationContext, "Linear Layout clicked", Toast.LENGTH_SHORT).show()
        }
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
