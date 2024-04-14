package com.example.bookapp.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookapp.R
import com.example.bookapp.databinding.ScreenBooksBinding
import com.example.bookapp.presentation.adapter.BookAdapter
import com.example.bookapp.presentation.viewModel.BookViewModel
import com.example.bookapp.presentation.viewModel.impl.BookViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BookScreen : Fragment(R.layout.screen_books) {

    private val binding by viewBinding(ScreenBooksBinding::bind)
    private val viewModel: BookViewModel by viewModels<BookViewModelImpl>()
    private val adapter = BookAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        adapter.setOnClickItem {
            findNavController().navigate(BookScreenDirections.actionBookScreenToReadScreen(data = it))
        }

        viewModel.getImages()

        viewModel.success.onEach {
            adapter.submitList(it)
        }.launchIn(lifecycleScope)

        viewModel.errorMessage.onEach {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)

    }


}