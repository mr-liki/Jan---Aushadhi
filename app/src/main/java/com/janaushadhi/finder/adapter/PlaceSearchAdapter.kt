package com.janaushadhi.finder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.R
import com.janaushadhi.finder.utils.PlaceSearchUtils

class PlaceSearchAdapter(
    private val onPlaceClick: (PlaceSearchUtils.PlaceResult) -> Unit
) : ListAdapter<PlaceSearchUtils.PlaceResult, PlaceSearchAdapter.PlaceViewHolder>(PlaceDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_place_search, parent, false)
        return PlaceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPlaceName: TextView = itemView.findViewById(R.id.tv_place_name)
        private val tvPlaceAddress: TextView = itemView.findViewById(R.id.tv_place_address)

        fun bind(place: PlaceSearchUtils.PlaceResult) {
            tvPlaceName.text = place.name
            tvPlaceAddress.text = place.address

            itemView.setOnClickListener {
                onPlaceClick(place)
            }
        }
    }

    private class PlaceDiffCallback : DiffUtil.ItemCallback<PlaceSearchUtils.PlaceResult>() {
        override fun areItemsTheSame(
            oldItem: PlaceSearchUtils.PlaceResult,
            newItem: PlaceSearchUtils.PlaceResult
        ): Boolean {
            return oldItem.latitude == newItem.latitude && oldItem.longitude == newItem.longitude
        }

        override fun areContentsTheSame(
            oldItem: PlaceSearchUtils.PlaceResult,
            newItem: PlaceSearchUtils.PlaceResult
        ): Boolean {
            return oldItem == newItem
        }
    }
}