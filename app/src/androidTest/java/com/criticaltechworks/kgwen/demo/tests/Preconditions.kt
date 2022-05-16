/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
@file:Suppress("unused")

package com.criticaltechworks.kgwen.demo.tests

import com.criticaltechworks.kgwen.demo.core.*
import com.criticaltechworks.kgwen.demo.tests.drawer.*
import com.criticaltechworks.kgwen.demo.tests.messages.selectFirstConversation
import com.criticaltechworks.kgwen.demo.tests.notifications.*
import com.criticaltechworks.kgwen.sections.Preconditions
import com.criticaltechworks.kgwen.utils.onNotAsserted
import com.criticaltechworks.kgwen.verbs.Actions

context(MainActivityScenarioTest)
fun Preconditions.goToMessages() {
    onNotAsserted {
        messagesVisible()
    } perform {
        openDrawer()
        selectMessages()
    }
}

context(MainActivityScenarioTest, ComposeTest)
fun Preconditions.goToFirstConversation() {
    onNotAsserted { messagesVisible() } perform { goToMessages() }
    Actions.selectFirstConversation()
}

context(MainActivityScenarioTest)
fun Preconditions.goToNotifications() {
    onNotAsserted {
        notificationsVisible()
    } perform {
        openDrawer()
        selectNotifications()
    }
}

context(MainActivityScenarioTest)
fun Preconditions.receiveNotification() {
    Actions.receiveMissedCallNotification()
}

context(MainActivityScenarioTest)
fun Preconditions.goToEvents() {
    onNotAsserted {
        eventsVisible()
    } perform {
        openDrawer()
        selectEvents()
    }
}
