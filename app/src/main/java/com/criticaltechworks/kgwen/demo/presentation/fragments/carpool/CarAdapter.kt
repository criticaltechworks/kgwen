/*
 * Copyright 2022 Critical TechWorks, SA
 * SPDX-License-Identifier: Apache-2.0
 */
package com.criticaltechworks.kgwen.demo.presentation.fragments.carpool

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.criticaltechworks.kgwen.demo.databinding.CarItemBinding
import com.criticaltechworks.kgwen.demo.model.SampleData

class CarAdapter : RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CarItemBinding
        .inflate(LayoutInflater.from(parent.context), parent, false)
        .run { ViewHolder(this) }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            with(SampleData.Cars.ALL[position]) {
                logo.setImageResource(brand.logo)
                car.setImageResource(picture)
            }
        }
    }

    override fun getItemCount() = SampleData.Cars.ALL.count()

    class ViewHolder(val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root)
}