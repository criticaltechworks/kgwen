/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.criticaltechworks.kgwen.demo.databinding.FragmentDetailedEventsBinding
import com.criticaltechworks.kgwen.demo.ktx.startEnterTransitionAfter
import com.criticaltechworks.kgwen.demo.viewmodels.EventsViewModel

class DetailedEventsFragment : Fragment() {
    private val viewModel: EventsViewModel by activityViewModels()
    private val detailedEventAdapter = DetailedEventAdapter()

    private lateinit var binding: FragmentDetailedEventsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedEventsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startEnterTransitionAfter {
            with(binding.detailedEventsList) {
                adapter = detailedEventAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
            viewModel.events.observe(viewLifecycleOwner) { detailedEventAdapter.update(it) }
            binding.addEvent.setOnClickListener {
                EventDialog().show(childFragmentManager, EventDialog.TAG)
            }
        }
    }
}
