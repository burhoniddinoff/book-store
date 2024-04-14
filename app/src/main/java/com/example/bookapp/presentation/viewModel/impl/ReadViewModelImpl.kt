package com.example.bookapp.presentation.viewModel.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.domain.AppRepository
import com.example.bookapp.presentation.viewModel.ReadViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ReadViewModelImpl @Inject constructor(
    private val repository: AppRepository,
) : ViewModel(), ReadViewModel {

    override val success = MutableSharedFlow<File>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    override val errorMessage = MutableSharedFlow<String>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)

    override fun downloadFile(path: File, file: String) {
        repository.downloadFile(path, file).onEach {
            it.onSuccess {

            }
            it.onFailure {

            }
        }.launchIn(viewModelScope)
    }

}