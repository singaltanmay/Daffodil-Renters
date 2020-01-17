package com.daffodil.renters.ui.browse

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.daffodil.renters.R
import com.daffodil.renters.ui.MenuBottomSheetDialogFragment
import com.daffodil.renters.ui.NavigationHost
import com.google.android.material.bottomappbar.BottomAppBar

class BrowseActivity : AppCompatActivity(),
    NavigationHost {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_activity)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.container,
                    ListBrowseFragment()
                )
                .commit()
        }

        initBottomNavBar()

    }


    private fun initBottomNavBar() {

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottom_app_bar)
        bottomAppBar.setOnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.action_item_filter_houses -> {
                    Toast.makeText(this, "Filter", Toast.LENGTH_SHORT).show()
                    return@setOnMenuItemClickListener true
                }
                else -> {
                    return@setOnMenuItemClickListener false
                }
            }

        }

        val linearLayout =
            findViewById<LinearLayout>(R.id.bottom_app_bar_content_container)
        linearLayout.setOnClickListener {

            MenuBottomSheetDialogFragment(R.menu.menu_house_display_layout) {
                when (it.itemId) {
                    R.id.action_display_type_map -> {
                        linearLayout.findViewById<TextView>(R.id.bottom_app_bar_title).text = "Map"
                        val imageView =
                            linearLayout.findViewById<ImageView>(R.id.bottom_app_bar_logo)
                        imageView.setImageResource(R.drawable.ic_map_black_24dp)
                        return@MenuBottomSheetDialogFragment true
                    }
                    R.id.action_display_type_list -> {
                        navigateTo(ListBrowseFragment(), false)
                        linearLayout.findViewById<TextView>(R.id.bottom_app_bar_title).text = "List"
                        val imageView =
                            linearLayout.findViewById<ImageView>(R.id.bottom_app_bar_logo)
                        imageView.setImageResource(R.drawable.ic_view_headline_black_24dp)
                        return@MenuBottomSheetDialogFragment true
                    }
                }
                false
            }.show(supportFragmentManager, null)

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
