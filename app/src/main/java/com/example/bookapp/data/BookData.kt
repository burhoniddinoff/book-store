package com.example.bookapp.data

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookData(
    val b1Name: String,
    val bookName: String,
    val authorName: String,
    val bookSize: String,
    val image: Bitmap,
) : Parcelable