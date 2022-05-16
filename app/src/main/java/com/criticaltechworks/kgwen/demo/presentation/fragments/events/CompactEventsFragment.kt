/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.events

import android.os.Bundle
import android.view.*
import androidx.fragment.app.*
import androidx.recyclerview.widget.GridLayoutManager
import com.criticaltechworks.kgwen.demo.databinding.FragmentCompactEventsBinding
import com.criticaltechworks.kgwen.demo.ktx.startEnterTransitionAfter
import com.criticaltechworks.kgwen.demo.viewmodels.EventsViewModel

class CompactEventsFragment : Fragment() {
    private val viewModel: EventsViewModel by activityViewModels()
    private val compactEventAdapter = CompactEventAdapter()

    private lateinit var binding: FragmentCompactEventsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCompactEventsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        startEnterTransitionAfter {
            with(binding.compactEventsGrid) {
                adapter = compactEventAdapter
                layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
                setHasFixedSize(true)
            }
            viewModel.events.observe(viewLifecycleOwner) { compactEventAdapter.update(it) }
            binding.addEvent.setOnClickListener {
                EventDialog().show(childFragmentManager, EventDialog.TAG)
            }
        }
    }

    companion object {
        const val SPAN_COUNT = 3
    }
}
