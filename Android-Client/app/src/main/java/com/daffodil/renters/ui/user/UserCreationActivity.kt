package com.daffodil.renters.ui.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.daffodil.renters.R

class UserCreationActivity : AppCompatActivity(), UserLoginFragment.OnUserLoginListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_creation_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, UserLoginFragment())
                .commitNow()
        }
    }

    override fun OnLogin(userId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
