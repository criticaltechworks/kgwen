/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.events

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.databinding.FragmentEventsBinding
import com.criticaltechworks.kgwen.demo.ktx.find

class EventsFragment : Fragment() {
    private lateinit var binding: FragmentEventsBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = childFragmentManager
            .find<NavHostFragment>(R.id.events_nav_host_fragment)
            .navController
        binding.eventsBottomNav.setupWithNavController(navController)
    }
}
