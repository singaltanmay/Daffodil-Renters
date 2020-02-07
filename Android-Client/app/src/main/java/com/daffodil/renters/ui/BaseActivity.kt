package com.daffodil.renters.ui

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.daffodil.renters.R
import kotlinx.android.synthetic.main.activity_base.*

class BaseActivity : AppCompatActivity(), AppBarConfiguration.OnNavigateUpListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val toolbar = base_activity_toolbar
        setSupportActionBar(toolbar)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupNavigation(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val dest: String = try {
                resources.getResourceName(destination.id)
            } catch (e: Resources.NotFoundException) {
                Integer.toString(destination.id)
            }
            Toast.makeText(
                this@BaseActivity, "Navigated to $dest",
                Toast.LENGTH_SHORT
            ).show()
            Log.d("NavigationActivity", "Navigated to $dest")
        }
    }


    private fun setupNavigation(navController: NavController) {
        val drawer = base_activity_navigation_drawer
        drawer?.setupWithNavController(navController)
        val drawerLayout = base_activity_drawer_layout

        //fragments load from here but how ?
        appBarConfiguration = AppBarConfiguration(
            setOf(
                /**
                 * All fragments that are at the top level of nagivation.
                 * Up button will not be shown for these fragments.
                  */
                R.id.userLoginFragment
            ),
            drawerLayout
        )

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val retValue = super.onCreateOptionsMenu(menu)
        val drawer = base_activity_navigation_drawer
        if (drawer == null) {
            //android needs to know what menu I need
            menuInflater.inflate(R.menu.navigation_drawer, menu)
            return true
        }
        return retValue
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        //I need to open the drawer onClick
        when (item!!.itemId) {
            android.R.id.home ->
                base_activity_drawer_layout.openDrawer(GravityCompat.START)
        }

        return item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        if (base_activity_drawer_layout.isDrawerOpen(GravityCompat.START)) {
            base_activity_drawer_layout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }


}
