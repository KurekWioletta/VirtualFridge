package com.example.virtualfridge.domain.main

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualfridge.R
import com.example.virtualfridge.domain.base.BaseActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSend.setOnClickListener {
            presenter.onSendClicked(etUsername.text.toString())
        }
    }

    // TODO: create generic loading state and move to base
    fun showLoading() {
        pbLoading.visibility = VISIBLE
    }

    fun hideLoading() {
        pbLoading.visibility = GONE
    }

    fun showResult(message: String) = Toast.makeText(this, message, Toast.LENGTH_LONG).show()

    companion object {
        fun getIntent(activity: BaseActivity) = Intent(activity, MainActivity::class.java)
    }
}
