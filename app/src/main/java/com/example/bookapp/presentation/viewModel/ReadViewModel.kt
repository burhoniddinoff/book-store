package com.example.bookapp.presentation.viewModel

import kotlinx.coroutines.flow.MutableSharedFlow
import java.io.File

interface ReadViewModel {

    val success: MutableSharedFlow<File>
    val errorMessage: MutableSharedFlow<String>

    fun downloadFile(path: File, file: String)

}