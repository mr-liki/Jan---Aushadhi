package com.janaushadhi.finder.ui.main

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.janaushadhi.finder.R
import com.janaushadhi.finder.adapter.MedicineAdapter
import com.janaushadhi.finder.ui.medicine.MedicineDetailActivity
import com.janaushadhi.finder.viewmodel.MedicineViewModel

class SearchFragmentFixed : Fragment() {

    private lateinit var viewModel: MedicineViewModel
    private lateinit var adapter: MedicineAdapter
    private lateinit var searchEditText: TextInputEditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_alternative, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[MedicineViewModel::class.java]

        searchEditText = view.findViewById(R.id.et_search)
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        emptyView = view.findViewById(R.id.empty_view)

        setupRecyclerView()
        setupSearchInput()
        observeViewModel()
        
        // Debug: Check if database has medicines
        viewModel.allMedicines.observe(viewLifecycleOwner) { medicines ->
            android.util.Log.d("SearchFragment", "Total medicines in database: ${medicines.size}")
            if (medicines.isEmpty()) {
                emptyView.text = "Database is loading... Please wait."
            }
        }
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

    private fun setupSearchInput() {
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s?.toString()?.trim() ?: ""
                if (query.isBlank()) {
                    viewModel.clearSearch()
                } else if (query.length >= 2) {
                    viewModel.searchMedicines(query)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
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
        val query = searchEditText.text?.toString()?.trim() ?: ""
        if (isEmpty && query.isNotBlank()) {
            emptyView.visibility = View.VISIBLE
            emptyView.text = getString(R.string.search_no_results)
        } else if (query.isBlank()) {
            emptyView.visibility = View.VISIBLE
            emptyView.text = getString(R.string.search_hint)
        } else {
            emptyView.visibility = View.GONE
        }
    }
}