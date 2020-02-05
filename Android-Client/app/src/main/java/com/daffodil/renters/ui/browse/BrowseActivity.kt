package com.daffodil.renters.ui.browse

import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.daffodil.renters.R
import com.daffodil.renters.application.RentersApplication
import com.daffodil.renters.ui.NavigationHost
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class BrowseActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener,
    NavigationHost {

    /**
     * Shared Preferences key used to store the display view type to render.
     */
    val BROWSE_ACTIVITY_DISPALY_TYPE_KEY = "browse_disp_type"

    /**
     * Values cannot be changed as they correspond to index in
     * values/strings.xml/layout_change_spinner_items array.
     */
    val BROWSE_HOMES_DISPLAY_TYPE_LIST = 0
    val BROWSE_HOMES_DISPLAY_TYPE_MAP = 1

    var spinnerEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_activity)

        val actionBar = findViewById<Toolbar>(R.id.browse_toolbar)
        setSupportActionBar(actionBar)
        actionBar.setTitleTextColor(resources.getColor(R.color.design_default_color_surface))

        if (savedInstanceState == null) initInitialFragment()
        setupFilterFab()

    }

    /**
     * Only enable spinner on first click.
     * If not disabled before fragment transaction app will always load in list mode
     */
    private fun initDisplayChangerSpinner(spinner: Spinner) {
        spinner.setSelection(browseHomesDisplayType)
        spinner.setOnTouchListener { _, _ ->
            spinnerEnabled = true
            return@setOnTouchListener false
        }
    }

    /**
     * Initialize the initial fragment based on previous display type selected by user.
     */
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

    /**
     * Setup top action bar by inflating menu
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.run { inflate(R.menu.browse_top_app_bar, menu) }
        val spinner = menu?.findItem(R.id.action_change_layout_spinner)?.actionView as Spinner

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.layout_change_spinner_items, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        initDisplayChangerSpinner(spinner)

        return true
    }

    /**
     * What to do if no option selected on spinner
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(this, "Nothing selected", Toast.LENGTH_SHORT).show()
    }

    /**
     * Set up logic to be executed when spinner item clicked
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!spinnerEnabled) return
        when (position) {
            0 -> {
                Thread {
                    navigateTo(ListBrowseFragment(), false)
                    browseHomesDisplayType = BROWSE_HOMES_DISPLAY_TYPE_LIST
                }.start()
            }
            1 -> {
                Thread {
                    navigateTo(MapBrowseFragment(), false)
                    browseHomesDisplayType = BROWSE_HOMES_DISPLAY_TYPE_MAP
                }.start()
            }
        }
    }

    /**
     * Setup the filter fab by setting click listener
     */
    private fun setupFilterFab() {
        val fab = findViewById<ExtendedFloatingActionButton>(R.id.browse_filter_fab)
        fab.setOnClickListener {
            FilterHousesBottomDialogFragment().show(
                supportFragmentManager,
                null
            )
        }
    }

    /**
     * Get and Set the value of default display mode in shared preferences.
     */
    var browseHomesDisplayType: Int
        get() {
            return (applicationContext as RentersApplication).getAppPreferences().getInt(
                BROWSE_ACTIVITY_DISPALY_TYPE_KEY,
                BROWSE_HOMES_DISPLAY_TYPE_LIST
            )
        }
        set(value) {
            (applicationContext as RentersApplication).getAppPreferences()
                .edit { putInt(BROWSE_ACTIVITY_DISPALY_TYPE_KEY, value) }
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
