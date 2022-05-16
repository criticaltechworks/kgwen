/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.notifications

import android.os.Bundle
import android.view.*
import androidx.core.view.isGone
import androidx.fragment.app.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.criticaltechworks.kgwen.demo.databinding.FragmentNotificationsBinding
import com.criticaltechworks.kgwen.demo.ktx.startEnterTransitionAfter
import com.criticaltechworks.kgwen.demo.model.SampleData
import com.criticaltechworks.kgwen.demo.viewmodels.NotificationViewModel

class NotificationsFragment : Fragment() {
    private val viewModel: NotificationViewModel by activityViewModels()
    private val notificationAdapter = NotificationAdapter(
        onMarkClick = { index, read -> whenNotAnimatingList { markAt(index, read) } },
        onDeleteClick = { index -> whenNotAnimatingList { removeAt(index) } }
    )

    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater)
        setupNotificationList()
        setupNewNotificationButton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startEnterTransitionAfter { setupObservers() }
    }

    private fun setupNotificationList() {
        with(binding.notificationList) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = notificationAdapter
        }
    }

    private fun setupNewNotificationButton() {
        binding.notificationButton.setOnClickListener {
            viewModel.send(notification = SampleData.notifications().random(), requireContext())
        }
    }

    private fun setupObservers() {
        viewModel.notifications.observe(viewLifecycleOwner) { notifications ->
            notificationAdapter.submitList(notifications)
            with(binding) {
                notificationList.isGone = notifications.isEmpty()
                noNotificationsMessage.isGone = !notificationList.isGone
                notificationList.layoutManager?.scrollToPosition(0)
            }
        }
    }

    private fun markAt(index: Int, read: Boolean) {
        viewModel.markAt(index, read)
        notificationAdapter.notifyItemChanged(index)
    }

    private fun removeAt(index: Int) {
        viewModel.removeNotificationAt(index)
        with(notificationAdapter) {
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, viewModel.notifications.value!!.count())
        }
    }

    /** Calls [block] when the notification list is not animating ensure item indices are valid. */
    private fun whenNotAnimatingList(block: () -> Unit) {
        if (!binding.notificationList.isAnimating) block()
    }
}
