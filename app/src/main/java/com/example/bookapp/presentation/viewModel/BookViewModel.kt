package com.example.bookapp.presentation.viewModel

import com.example.bookapp.data.BookData
import kotlinx.coroutines.flow.MutableSharedFlow

interface BookViewModel {

    val success: MutableSharedFlow<List<BookData>>
    val errorMessage: MutableSharedFlow<String>

    fun getImages()
}