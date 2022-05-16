/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.carpool

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.*
import com.criticaltechworks.kgwen.demo.databinding.FragmentCarpoolBinding
import com.criticaltechworks.kgwen.demo.ktx.*
import com.criticaltechworks.kgwen.demo.model.SampleData
import kotlin.math.abs

class CarpoolFragment : Fragment() {
    private val carAdapter = CarAdapter()
    private lateinit var binding: FragmentCarpoolBinding

    private val pageTransformer
        get() = CompositePageTransformer().apply {
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.15f
            }
            addTransformer { page, position ->
                val offset = position * -(2 * OFFSET_Y.dpToPx())
                page.translationX = if (binding.carPager.isRTL) -offset else offset
            }
        }

    private val pageObserver
        get() = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.switcher.setText(SampleData.Cars.ALL[position].model)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCarpoolBinding.inflate(inflater)
        setupViewPager()
        setupTextSwitcher()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startEnterTransitionOnPreDraw()
    }

    private fun setupViewPager() = with(binding.carPager) {
        adapter = carAdapter
        orientation = ViewPager2.ORIENTATION_HORIZONTAL
        offscreenPageLimit = 3
        children.first().overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        registerOnPageChangeCallback(pageObserver)
        setPageTransformer(pageTransformer)
        binding.indicator.setViewPager2(this)
    }

    private fun setupTextSwitcher() {
        with(binding.switcher) {
            setFactory {
                TextView(requireContext()).apply {
                    setTextAppearance(android.R.style.TextAppearance_Material_Display2)
                    gravity = Gravity.CENTER_HORIZONTAL
                    textSize = 35f
                }
            }
            inAnimation = loadAnimation(androidx.appcompat.R.anim.abc_fade_in)
            outAnimation = loadAnimation(androidx.appcompat.R.anim.abc_fade_out)
        }
    }

    private companion object {
        const val OFFSET_Y = 20
    }
}
