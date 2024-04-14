package com.example.bookapp.domain

import android.graphics.BitmapFactory
import android.util.Log
import com.example.bookapp.data.BookData
import com.google.firebase.Firebase
import com.google.firebase.storage.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor() {

    private val storage = Firebase.storage
    private val storageRef = storage.reference

    fun getImages(): Flow<Result<List<BookData>>> = callbackFlow {
        storageRef.child("images/").listAll()
            .addOnSuccessListener { listResult ->
                val images = ArrayList<BookData>()
                var size = listResult.items.size
                listResult.items.forEach { it ->

                    val data = it.name.split("$")
                    it.getBytes(10 * 1024 * 1024).addOnSuccessListener {
                        val matrix = BitmapFactory.decodeByteArray(it, 0, it.size)

                        images.add(BookData(data[0], data[1], data[2], data[3], matrix))
                        size--

                        if (size == 0) trySend(Result.success(images))
                    }.addOnFailureListener {
                        trySend(Result.failure(it))

                    }
                }
            }
            .addOnFailureListener {
                trySend(Result.failure(it))
            }

        awaitClose()
    }


    fun downloadFile(path: File, file: String): Flow<Result<File>> = callbackFlow {

        storageRef.child("books/$file.pdf")
            .getFile(path)
            .addOnSuccessListener {
                trySend(Result.success(path))
            }
            .addOnProgressListener {
                Log.d("TTT", "downloading ${it.bytesTransferred * 100 / it.totalByteCount}")
            }
            .addOnFailureListener {
                trySend(Result.failure(Throwable(it.message)))
            }

        awaitClose()

    }

}