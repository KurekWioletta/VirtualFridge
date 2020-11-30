package com.example.virtualfridge.domain.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.virtualfridge.R
import com.example.virtualfridge.domain.base.BaseActivity
import com.example.virtualfridge.domain.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : BaseActivity() {

    @Inject
    lateinit var presenter: MainActivityPresenter

    @Inject
    lateinit var adapter: MainActivityAdapter

    @Inject
    lateinit var fragmentManager: FragmentManager

    private var selectedPosition = -1
    private var activeFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_board -> selectPage(0)
                R.id.action_calendar -> selectPage(1)
                R.id.action_family -> selectPage(2)
            }
            true
        }
        selectPage(0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(SettingsActivity.getIntent(this))
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun selectPage(position: Int) {
        // TODO: cache current page properly
        if (selectedPosition != position) {
            selectedPosition = position

            var selectedFragment = fragmentManager.findFragmentByTag(tagForFragment(position))
            val transaction = fragmentManager.beginTransaction().setReorderingAllowed(true)

            if (selectedFragment == null) {
                selectedFragment = adapter.getItem(position)
                transaction.add(
                    R.id.flFragmentContainer,
                    selectedFragment,
                    tagForFragment(position)
                )
            }
            if (activeFragment != null) {
                transaction.hide(activeFragment as Fragment)
            }
            transaction.show(selectedFragment).commit()
            activeFragment = selectedFragment
        }
    }

    private fun tagForFragment(position: Int) = "MainActivityFragment$position"

    companion object {
        fun getIntent(activity: BaseActivity) = Intent(activity, MainActivity::class.java)

        fun getIntentWithClearStack(activity: BaseActivity) =
            Intent(
                activity,
                MainActivity::class.java
            ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}
