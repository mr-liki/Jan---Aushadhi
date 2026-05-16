package com.janaushadhi.finder.ui.medicine

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.janaushadhi.finder.R
import com.janaushadhi.finder.data.database.AppDatabase
import com.janaushadhi.finder.data.model.Medicine
import com.janaushadhi.finder.data.repository.MedicineRepository
import com.janaushadhi.finder.utils.CurrencyUtils
import kotlinx.coroutines.launch

class MedicineDetailActivity : AppCompatActivity() {

    private lateinit var repository: MedicineRepository
    private var medicine: Medicine? = null

    private lateinit var brandName: TextView
    private lateinit var genericName: TextView
    private lateinit var saltComposition: TextView
    private lateinit var brandedPrice: TextView
    private lateinit var genericPrice: TextView
    private lateinit var savings: TextView
    private lateinit var category: TextView
    private lateinit var uses: TextView
    private lateinit var sideEffects: TextView
    private lateinit var favoriteIcon: ImageView
    private lateinit var fabAddReminder: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_medicine_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val database = AppDatabase.getDatabase(this)
        repository = MedicineRepository(database.medicineDao())

        initViews()

        val medicineId = intent.getIntExtra("medicine_id", -1)
        if (medicineId != -1) {
            loadMedicine(medicineId)
        }
    }

    private fun initViews() {
        brandName = findViewById(R.id.tv_brand_name)
        genericName = findViewById(R.id.tv_generic_name)
        saltComposition = findViewById(R.id.tv_salt_composition)
        brandedPrice = findViewById(R.id.tv_branded_price)
        genericPrice = findViewById(R.id.tv_generic_price)
        savings = findViewById(R.id.tv_savings)
        category = findViewById(R.id.tv_category)
        uses = findViewById(R.id.tv_uses)
        sideEffects = findViewById(R.id.tv_side_effects)
        favoriteIcon = findViewById(R.id.iv_favorite)
        fabAddReminder = findViewById(R.id.fab_add_reminder)
    }

    private fun loadMedicine(id: Int) {
        lifecycleScope.launch {
            medicine = repository.getMedicineById(id)
            medicine?.let { displayMedicine(it) }
        }
    }

    private fun displayMedicine(med: Medicine) {
        supportActionBar?.title = med.brandName

        brandName.text = med.brandName
        genericName.text = "Generic: ${med.genericName}"
        saltComposition.text = "Salt: ${med.saltComposition}"
        brandedPrice.text = CurrencyUtils.formatPrice(med.brandedPrice)
        genericPrice.text = CurrencyUtils.formatPrice(med.genericPrice)
        savings.text = CurrencyUtils.formatSavings(med.brandedPrice, med.genericPrice)
        category.text = med.category
        uses.text = med.uses.ifBlank { "Not specified" }
        sideEffects.text = med.sideEffects.ifBlank { "Consult your doctor" }

        favoriteIcon.setImageResource(
            if (med.isFavorite) R.drawable.ic_favorite_filled
            else R.drawable.ic_favorite_border
        )

        favoriteIcon.setOnClickListener {
            lifecycleScope.launch {
                repository.toggleFavorite(med)
                loadMedicine(med.id)
            }
        }

        fabAddReminder.setOnClickListener {
            Toast.makeText(this, "Reminder feature - Add to RemindersFragment", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
