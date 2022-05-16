/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.ktx

import android.view.animation.*
import androidx.annotation.*
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.transition.*

/** Postpone enter transition and start it only after [block] returns. */
fun Fragment.startEnterTransitionAfter(block: () -> Unit) {
    postponeEnterTransition()
    block()
    requireView().doOnPreDraw { startPostponedEnterTransition() }
}

/** Postpone enter transition and start it as soon as the root view is pre-drawn. */
fun Fragment.startEnterTransitionOnPreDraw() {
    postponeEnterTransition()
    requireView().doOnPreDraw { startPostponedEnterTransition() }
}

/** Inflate a [Transition] from the transition [resource]. */
fun Fragment.inflateTransition(@TransitionRes resource: Int): Transition = TransitionInflater
    .from(requireContext())
    .inflateTransition(resource)

/** Load an animation using this [Fragment]'s context. */
fun Fragment.loadAnimation(@AnimRes id: Int): Animation =
    AnimationUtils.loadAnimation(requireContext(), id)
