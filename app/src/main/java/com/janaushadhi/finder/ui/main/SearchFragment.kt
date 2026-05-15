package com.janaushadhi.finder.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.janaushadhi.finder.R
import com.janaushadhi.finder.adapter.MedicineAdapter
import com.janaushadhi.finder.ui.medicine.MedicineDetailActivity
import com.janaushadhi.finder.viewmodel.MedicineViewModel

class SearchFragment : Fragment() {

    private lateinit var viewModel: MedicineViewModel
    private lateinit var adapter: MedicineAdapter
    private lateinit var searchView: SearchView
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MedicineViewModel::class.java]

        searchView = view.findViewById(R.id.search_view)
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        emptyView = view.findViewById(R.id.empty_view)

        setupRecyclerView()
        setupSearchView()
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

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchMedicines(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    viewModel.clearSearch()
                } else if (newText.length >= 2) {
                    viewModel.searchMedicines(newText)
                }
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.searchResults.observe(viewLifecycleOwner) { medicines ->
            adapter.submitList(medicines)
            updateEmptyView(medicines.isEmpty())
        }

        viewModel.isSearching.observe(viewLifecycleOwner) { isSearching ->
            progressBar.visibility = if (isSearching) View.VISIBLE else View.GONE
        }
    }

    private fun updateEmptyView(isEmpty: Boolean) {
        if (isEmpty && searchView.query.isNotBlank()) {
            emptyView.visibility = View.VISIBLE
            emptyView.text = getString(R.string.search_no_results)
        } else if (searchView.query.isBlank()) {
            emptyView.visibility = View.VISIBLE
            emptyView.text = getString(R.string.search_hint)
        } else {
            emptyView.visibility = View.GONE
        }
    }
}
