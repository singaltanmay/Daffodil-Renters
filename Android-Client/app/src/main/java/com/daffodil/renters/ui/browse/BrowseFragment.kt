package com.daffodil.renters.ui.browse

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.daffodil.renters.R
import com.daffodil.renters.application.RentersApplication
import com.daffodil.renters.ui.NavigationHost
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.android.synthetic.main.browse_fragment.*

class BrowseFragment : Fragment(), AdapterView.OnItemSelectedListener,
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
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.browse_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) initInitialFragment()
        setupFilterFab()
    }

    /**
     * Only enable spinner on first click.
     * If not disabled before fragment transaction app will always load in list mode
     */
    private fun initDisplayChangerSpinner(spinner: Spinner) {
        Thread { spinner.setSelection(browseHomesDisplayType) }.start()
        Thread {
            spinner.setOnTouchListener { _, _ ->
                spinnerEnabled = true
                return@setOnTouchListener false
            }
        }.start()
    }

    /**
     * Initialize the initial fragment based on previous display type selected by user.
     */
    private fun initInitialFragment() {
        val fragment: ControllerFragment

        when (browseHomesDisplayType) {
            BROWSE_HOMES_DISPLAY_TYPE_LIST -> {
                fragment = ListControllerFragment()
            }
            BROWSE_HOMES_DISPLAY_TYPE_MAP -> {
                fragment = MapControllerFragment()
            }
            else -> fragment = ListControllerFragment()
        }

        navigateTo(fragment)

    }

    /**
     * Modify top action bar of parent activity by inflating custom menu
     */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.browse_top_app_bar, menu)

        val spinner = menu.findItem(R.id.action_change_layout_spinner)?.actionView as Spinner
        val adapter = ArrayAdapter.createFromResource(
            context!!,
            R.array.layout_change_spinner_items, android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        initDisplayChangerSpinner(spinner)

        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * What to do if no option selected on spinner
     */
    override fun onNothingSelected(parent: AdapterView<*>?) {
        Toast.makeText(context, "Nothing selected", Toast.LENGTH_SHORT).show()
    }

    /**
     * Set up logic to be executed when spinner item clicked
     */
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (!spinnerEnabled) return
        when (position) {
            0 -> {
                Thread {
                    navigateTo(ListControllerFragment())
                    browseHomesDisplayType = BROWSE_HOMES_DISPLAY_TYPE_LIST
                }.start()
            }
            1 -> {
                Thread {
                    navigateTo(MapControllerFragment())
                    browseHomesDisplayType = BROWSE_HOMES_DISPLAY_TYPE_MAP
                }.start()
            }
        }
    }

    /**
     * Setup the filter fab by setting click listener
     */
    private fun setupFilterFab() {
        val fab: ExtendedFloatingActionButton = browse_filter_fab
        Thread {
            fab.setOnClickListener {
                FilterHousesBottomDialogFragment().show(
                    activity!!.supportFragmentManager,
                    null
                )
            }
        }.start()
    }

    /**
     * Get and Set the value of default display mode in shared preferences.
     */
    var browseHomesDisplayType: Int
        get() {
            return (activity?.applicationContext as RentersApplication).getAppPreferences().getInt(
                BROWSE_ACTIVITY_DISPALY_TYPE_KEY,
                BROWSE_HOMES_DISPLAY_TYPE_LIST
            )
        }
        set(value) {
            (activity?.applicationContext as RentersApplication).getAppPreferences()
                .edit { putInt(BROWSE_ACTIVITY_DISPALY_TYPE_KEY, value) }
        }

    /**
     * Navigate to the given fragment.
     *
     * @param fragment       Fragment to navigate to.
     * @param addToBackstack Whether or not the current fragment should be added to the backstack.
     */
    override fun navigateTo(fragment: Fragment, addToBackstack: Boolean) {
        val transaction = activity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)

        if (addToBackstack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }
}
