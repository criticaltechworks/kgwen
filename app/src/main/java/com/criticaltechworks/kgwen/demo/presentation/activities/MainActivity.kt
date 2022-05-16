/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("UNCHECKED_CAST")

package com.criticaltechworks.kgwen.demo.presentation.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.viewModels
import androidx.appcompat.app.*
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.databinding.*
import com.criticaltechworks.kgwen.demo.ktx.*
import com.criticaltechworks.kgwen.demo.model.*
import com.criticaltechworks.kgwen.demo.presentation.fragments.LogoutDialog
import com.criticaltechworks.kgwen.demo.viewmodels.NotificationViewModel

class MainActivity : AppCompatActivity() {
    private val notificationViewModel: NotificationViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private val startForLoginResult =
        registerForActivityResult(StartActivityForResult()) { result ->
            when (result.resultCode) {
                LoginActivity.FAILURE -> finish()
                else -> navController.navigateTo(R.id.homeFragment, clearStack = true)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = supportFragmentManager
            .find<NavHostFragment>(R.id.nav_host_fragment)
            .navController

        setupLoginActivity()
        setupToolbar()
        setupDrawer()
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    @Suppress("unused")
    fun send(notification: Notification) = notificationViewModel.send(notification, context = this)

    private fun setupLoginActivity() {
        Profile.user.distinctUntilChanged().observe(this) { user ->
            if (user == null) {
                startForLoginResult.launch(Intent(this, LoginActivity::class.java))
            }
        }
    }

    private fun setupToolbar() {
        appBarConfiguration = AppBarConfiguration(TOP_DESTINATIONS, binding.drawerLayout)

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        drawerToggle =
            ActionBarDrawerToggle(this, binding.drawerLayout, R.string.open, R.string.close)
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupDrawer() {
        // Drawer header
        Profile.user.observe(this) { setupUserProfile(it) }

        with(binding.navView) {
            // Top level destinations
            setupWithNavController(navController)

            // Custom buttons
            menu.findItem(R.id.logout)
                .setOnMenuItemClickListener {
                    showLogoutDialog()
                    true
                }
        }
    }

    private fun setupUserProfile(user: User?) {
        with(NavHeaderBinding.bind(binding.navView.getHeaderView(0))) {
            profilePicture.setImageResource(user?.picture ?: R.color.primaryColor)
            displayName.text = user?.displayName ?: ""
            email.text = user?.email ?: ""
        }
    }

    private fun showLogoutDialog() {
        LogoutDialog().show(supportFragmentManager, LogoutDialog.TAG)
    }

    private companion object {
        val TOP_DESTINATIONS = setOf(
            R.id.homeFragment,
            R.id.messagesFragment,
            R.id.eventsFragment,
            R.id.notificationsFragment,
            R.id.carpoolFragment
        )
    }
}
