package com.example.bookapp.presentation.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.BookData
import com.example.bookapp.domain.AppRepository
import com.example.bookapp.presentation.viewModel.BookViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BookViewModelImpl @Inject constructor(
    private val repository: AppRepository,
) : ViewModel(), BookViewModel {

    override val success = MutableSharedFlow<List<BookData>>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorMessage = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)


    override fun getImages() {
        repository.getImages().onEach {
                it.onSuccess { data ->
                    success.emit(data)
                }
                it.onFailure { throwable ->
                    throwable.message?.let { it ->
                        errorMessage.emit(it)
                    }
                }
            }.launchIn(viewModelScope)
    }

}