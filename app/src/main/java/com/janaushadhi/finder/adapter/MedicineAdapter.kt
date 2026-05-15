package com.janaushadhi.finder.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.R
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.utils.CurrencyUtils

class MedicineAdapter(
    private val onItemClick: (Medicine) -> Unit,
    private val onFavoriteClick: (Medicine) -> Unit
) : ListAdapter<Medicine, MedicineAdapter.MedicineViewHolder>(MedicineDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandName: TextView = itemView.findViewById(R.id.tv_brand_name)
        private val genericName: TextView = itemView.findViewById(R.id.tv_generic_name)
        private val brandedPrice: TextView = itemView.findViewById(R.id.tv_branded_price)
        private val genericPrice: TextView = itemView.findViewById(R.id.tv_generic_price)
        private val savings: TextView = itemView.findViewById(R.id.tv_savings)
        private val category: TextView = itemView.findViewById(R.id.tv_category)
        private val favoriteIcon: ImageView = itemView.findViewById(R.id.iv_favorite)

        fun bind(medicine: Medicine) {
            brandName.text = medicine.brandName
            genericName.text = medicine.genericName
            brandedPrice.text = CurrencyUtils.formatPrice(medicine.brandedPrice)
            genericPrice.text = CurrencyUtils.formatPrice(medicine.genericPrice)
            savings.text = CurrencyUtils.formatSavings(medicine.brandedPrice, medicine.genericPrice)
            category.text = medicine.category

            favoriteIcon.setImageResource(
                if (medicine.isFavorite) R.drawable.ic_favorite_filled
                else R.drawable.ic_favorite_border
            )

            itemView.setOnClickListener { onItemClick(medicine) }
            favoriteIcon.setOnClickListener { onFavoriteClick(medicine) }
        }
    }

    private class MedicineDiffCallback : DiffUtil.ItemCallback<Medicine>() {
        override fun areItemsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Medicine, newItem: Medicine): Boolean {
            return oldItem == newItem
        }
    }
}
