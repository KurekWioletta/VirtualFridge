package com.example.virtualfridge.domain.settings

import android.content.Intent
import android.os.Bundle
import com.example.virtualfridge.R
import com.example.virtualfridge.domain.base.BaseActivity
import com.example.virtualfridge.domain.login.LoginActivity
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : BaseActivity() {

    @Inject
    lateinit var presenter: SettingsActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        btnLogout.setOnClickListener {
            presenter.logout()
        }
    }

    fun openLoginActivity() {
        startActivity(LoginActivity.getIntent(this))
        finish()
    }

    companion object {
        fun getIntent(activity: BaseActivity) = Intent(activity, SettingsActivity::class.java)
    }
}
