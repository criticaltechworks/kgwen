/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.criticaltechworks.kgwen.demo.R
import com.criticaltechworks.kgwen.demo.model.*

/**
 * A list that previews all available [conversations] and executes the [onConversationClick] block
 * when selected.
 */
@Composable
fun MessageList(
    conversations: List<Conversation>,
    onConversationClick: (Conversation) -> Unit = {}
) {
    LazyColumn {
        itemsIndexed(conversations) { index, conversation ->
            ConversationItem(
                conversation = conversation,
                modifier = Modifier
                    .clickable { onConversationClick(conversation) }
                    .withDescription(
                        R.string.conversation_description,
                        conversation.user.displayName
                    )
            )
            if (index != conversations.lastIndex) {
                Divider(thickness = 0.75.dp, startIndent = 100.dp)
            }
        }
    }
}

@Composable
private fun MessageListPreview() {
    AppTheme {
        MessageList(conversations = SampleData.CONVERSATIONS)
    }
}

@Preview(showBackground = true)
@Composable
fun MessageListLightPreview() {
    MessageListPreview()
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MessageListDarkPreview() {
    MessageListPreview()
}
