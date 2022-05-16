/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.model

/** A Conversation with another [user]. */
data class Conversation(
    val user: User,
    var messages: MutableList<Message>
) {
    /** A Message sent by the [sender] user. */
    data class Message(val sender: User, val content: String)
}

/**
 * Checks whether this message was received or sent in the [conversation] context.
 * @return true if the message was received, false otherwise.
 */
fun Conversation.Message.receivedIn(conversation: Conversation) =
    conversation.user.email == sender.email
