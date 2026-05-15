package com.janaushadhi.finder.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.R
import com.janaushadhi.finder.adapter.MedicineAdapter
import com.janaushadhi.finder.ui.medicine.MedicineDetailActivity
import com.janaushadhi.finder.viewmodel.MedicineViewModel

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: MedicineViewModel
    private lateinit var adapter: MedicineAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var emptyView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MedicineViewModel::class.java]

        recyclerView = view.findViewById(R.id.recycler_view)
        emptyView = view.findViewById(R.id.empty_view)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = MedicineAdapter(
            onItemClick = { medicine ->
                val intent = Intent(requireContext(), MedicineDetailActivity::class.java)
                intent.putExtra("medicine_id", medicine.id)
                startActivity(intent)
            },
            onFavoriteClick = { medicine ->
                viewModel.toggleFavorite(medicine)
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.favoriteMedicines.observe(viewLifecycleOwner) { medicines ->
            adapter.submitList(medicines)
            updateEmptyView(medicines.isEmpty())
        }
    }

    private fun updateEmptyView(isEmpty: Boolean) {
        emptyView.visibility = if (isEmpty) View.VISIBLE else View.GONE
        recyclerView.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }
}
