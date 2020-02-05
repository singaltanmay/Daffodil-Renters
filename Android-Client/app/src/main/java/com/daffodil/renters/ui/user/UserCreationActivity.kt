package com.daffodil.renters.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daffodil.renters.R

class UserCreationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_creation_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserCreationFragment.newInstance())
                .commitNow()
        }
    }

}
