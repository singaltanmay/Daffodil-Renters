package com.daffodil.renters.ui.settings

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.daffodil.renters.R


class SettingsFragment : PreferenceFragmentCompat() {

    private val LOG_TAG = SettingsFragment::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Change context of shared preferences to be accessible application wide
        PreferenceManager.setDefaultValues(
            context?.applicationContext,
            R.xml.root_preferences,
            false
        )
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {

        // Return false if you want preference to be updated

        when (preference?.key) {
            getString(R.string.server_ip_address_key) -> {
                // Return true if you want preference to be updated
                preference.setOnPreferenceChangeListener { preference, newValue ->
                    if (preference is EditTextPreference) {
                        Toast.makeText(
                            context,
                            "New ip ${newValue as String}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // Update IP address if string is a valid ip address
                    val matches = Patterns.IP_ADDRESS.matcher(newValue as String).matches()
                    matches
                }
            }

        }

        Log.v(LOG_TAG, preference?.key ?: "Pref is null")


        return false

    }
}