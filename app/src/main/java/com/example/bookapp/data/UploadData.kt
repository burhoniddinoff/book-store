package com.example.bookapp.data

sealed interface UploadData {
    data object SUCCESS : UploadData
    data object PAUSE: UploadData
    data object RESUME : UploadData
    data object CANCEL: UploadData

    data class Error(
        val message: String
    ) : UploadData

    data class Progress(
        val uploadBytes: Long,
        val totalBytes: Long
    ) : UploadData
}