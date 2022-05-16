/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.compose.runtime.*
import androidx.compose.ui.platform.*
import com.criticaltechworks.kgwen.demo.model.Conversation
import com.criticaltechworks.kgwen.demo.presentation.composables.*

/**
 * Compose [View] wrapper for [MessageList].
 */
class MessageListView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    var conversations: List<Conversation> by mutableStateOf(emptyList())
    var onConversationClick: (Conversation) -> Unit by mutableStateOf({ })

    init {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    }

    // Workaround for exitTransition which inadvertently calls super.
    override fun addView(child: View?, index: Int) {}

    @Composable
    override fun Content() {
        AppTheme {
            MessageList(
                conversations = conversations,
                onConversationClick = onConversationClick
            )
        }
    }
}
