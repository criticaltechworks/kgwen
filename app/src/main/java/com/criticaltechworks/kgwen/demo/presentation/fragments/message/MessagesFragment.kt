/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.message

import android.os.Bundle
import android.view.*
import androidx.fragment.app.*
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.databinding.FragmentMessagesBinding
import com.criticaltechworks.kgwen.demo.ktx.*
import com.criticaltechworks.kgwen.demo.model.*
import com.criticaltechworks.kgwen.demo.viewmodels.MessagesViewModel

class MessagesFragment : Fragment() {
    private val viewModel: MessagesViewModel by activityViewModels()

    private lateinit var binding: FragmentMessagesBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fade = inflateTransition(android.R.transition.fade)
            .apply { duration = resources.shortAnimationTime }
        enterTransition = fade
        exitTransition = fade
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMessagesBinding.inflate(inflater)
        with(binding.messageList) {
            onConversationClick = { conversation ->
                viewModel.conversation = conversation
                navController.navigate(directionsFor(conversation))
            }
            conversations = SampleData.CONVERSATIONS
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startEnterTransitionAfter {
            navController = parentFragmentManager
                .findFragmentById(R.id.nav_host_fragment)
                ?.findNavController()!!

        }
    }

    private fun directionsFor(conversation: Conversation) = MessagesFragmentDirections
        .actionMessagesFragmentToConversationFragment(conversation.user.displayName)
}
