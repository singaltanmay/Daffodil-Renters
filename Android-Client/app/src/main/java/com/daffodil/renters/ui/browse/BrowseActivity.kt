package com.daffodil.renters.ui.browse

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.daffodil.renters.R
import com.daffodil.renters.application.RentersApplication
import com.daffodil.renters.ui.MenuBottomSheetDialogFragment
import com.daffodil.renters.ui.NavigationHost
import com.daffodil.renters.ui.settings.SettingsActivity
import com.google.android.material.bottomappbar.BottomAppBar

class BrowseActivity : AppCompatActivity(),
    NavigationHost {

    private lateinit var linearLayout: LinearLayout

    val BROWSE_HOMES_DISPALY_TYPE_KEY = "fnw89ht3"

    val BROWSE_HOMES_DISPLAY_TYPE_LIST = 1
    val BROWSE_HOMES_DISPLAY_TYPE_MAP = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_activity)

        linearLayout = findViewById(R.id.bottom_app_bar_content_container)

        if (savedInstanceState == null) initInitialFragment()

        initBottomNavBar()

    }

    override fun onStart() {
        super.onStart()
        initBottomNavBarDisplaySwitcher()
    }

    private fun initInitialFragment() {
        val fragment: BrowseFragmentBase

        when (browseHomesDisplayType) {
            BROWSE_HOMES_DISPLAY_TYPE_LIST -> {
                fragment = ListBrowseFragment()
            }
            BROWSE_HOMES_DISPLAY_TYPE_MAP -> {
                fragment = MapBrowseFragment()
            }
            else -> fragment = ListBrowseFragment()
        }

        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.container,
                fragment
            )
            .commit()

    }

    private fun initBottomNavBarDisplaySwitcher() {
        when (browseHomesDisplayType) {
            BROWSE_HOMES_DISPLAY_TYPE_LIST -> {
                setDisplayTogglerViews(BROWSE_HOMES_DISPLAY_TYPE_LIST)
            }
            BROWSE_HOMES_DISPLAY_TYPE_MAP -> {
                setDisplayTogglerViews(BROWSE_HOMES_DISPLAY_TYPE_MAP)
            }
        }
    }

    private fun initBottomNavBar() {

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottom_app_bar)
        bottomAppBar.setOnMenuItemClickListener { item ->

            when (item.itemId) {
                R.id.action_item_filter_houses -> {
                    FilterHousesBottomDialogFragment().show(supportFragmentManager, null)
                    true
                }
                R.id.action_open_app_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                else -> {
                    false
                }
            }

        }

        linearLayout.setOnClickListener {

            MenuBottomSheetDialogFragment(R.menu.menu_house_display_layout) {
                when (it.itemId) {
                    R.id.action_display_type_map -> {
                        navigateTo(MapBrowseFragment(), false)
                        setDisplayTogglerViews(BROWSE_HOMES_DISPLAY_TYPE_MAP)
                        browseHomesDisplayType =
                            BROWSE_HOMES_DISPLAY_TYPE_MAP
                        return@MenuBottomSheetDialogFragment true
                    }
                    R.id.action_display_type_list -> {
                        navigateTo(ListBrowseFragment(), false)
                        setDisplayTogglerViews(BROWSE_HOMES_DISPLAY_TYPE_LIST)
                        browseHomesDisplayType =
                            BROWSE_HOMES_DISPLAY_TYPE_LIST
                        return@MenuBottomSheetDialogFragment true
                    }
                }
                false
            }.show(supportFragmentManager, null)

        }
    }

    private fun setDisplayTogglerViews(viewType: Int) {

        when (viewType) {
            BROWSE_HOMES_DISPLAY_TYPE_MAP -> {
                linearLayout.findViewById<TextView>(R.id.bottom_app_bar_title).text = "Map"
                val imageView =
                    linearLayout.findViewById<ImageView>(R.id.bottom_app_bar_logo)
                imageView.setImageResource(R.drawable.ic_map_black_24dp)
            }
            BROWSE_HOMES_DISPLAY_TYPE_LIST -> {
                linearLayout.findViewById<TextView>(R.id.bottom_app_bar_title).text = "List"
                val imageView =
                    linearLayout.findViewById<ImageView>(R.id.bottom_app_bar_logo)
                imageView.setImageResource(R.drawable.ic_view_headline_black_24dp)
            }
        }

    }


    var browseHomesDisplayType: Int
        get() {
            return (applicationContext as RentersApplication).getAppPreferences().getInt(
                BROWSE_HOMES_DISPALY_TYPE_KEY,
                BROWSE_HOMES_DISPLAY_TYPE_LIST
            )
        }
        set(value) {
            (applicationContext as RentersApplication).getAppPreferences()
                .edit { putInt(BROWSE_HOMES_DISPALY_TYPE_KEY, value) }
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
