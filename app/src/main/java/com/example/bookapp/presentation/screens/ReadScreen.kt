package com.example.bookapp.presentation.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.bookapp.R
import com.example.bookapp.databinding.ScreenReadBinding
import com.example.bookapp.domain.AppRepository
import com.example.bookapp.myLog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File

@AndroidEntryPoint
class ReadScreen : Fragment(R.layout.screen_read) {

    private val binding by viewBinding(ScreenReadBinding::bind)
    private val navArgs: ReadScreenArgs by navArgs()
    private val repository = AppRepository()

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            downloadFile()
        } else {
            Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        "log ishladi".myLog()
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            downloadFile()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

    }

    private fun downloadFile() {
        val bookData = navArgs.data

        val document = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        "document name ${document.name}".myLog()

        var isHaveBook = false

        val files = document.listFiles()

        if (files?.isNotEmpty() == true) {
            for (index in files.indices) {
                if (files[index].name.startsWith(bookData.b1Name)) {
                    isHaveBook = true

                    Toast.makeText(requireContext(), "${files[index].name}", Toast.LENGTH_SHORT).show()

                    "topildi ${files[index].name}".myLog()

                    binding.pdfView.recycle()
                    binding.pdfView
                        .fromFile(files[index])
                        .enableSwipe(false)
                        .swipeHorizontal(true)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .load()

                    break
                }
            }

        }


        if (!isHaveBook) {
            val temp = File.createTempFile(bookData.b1Name, ".pdf", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS))

            repository.downloadFile(temp, bookData.b1Name).onEach { it ->
                it.onSuccess {

                    binding.pdfView
                        .fromFile(it)
                        .enableSwipe(false)
                        .swipeHorizontal(true)
                        .enableDoubletap(true)
                        .defaultPage(0)
                        .load()

                    Toast.makeText(requireContext(), it.absolutePath, Toast.LENGTH_SHORT).show()
                }

                it.onFailure {
                    Log.d("TTT", "download error: ${it.message.toString()}")
                }
            }.launchIn(lifecycleScope)
        }


    }
}