package com.example.bookapp.presentation.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.bookapp.data.BookData
import com.example.bookapp.databinding.ItemBookBinding

class BookAdapter : ListAdapter<BookData, BookAdapter.BookViewHolder>(BookDiffUtil) {

    private var onClickItem: ((BookData) -> Unit)? = null
    private var onLongClickItem: ((BookData) -> Unit)? = null

    object BookDiffUtil : DiffUtil.ItemCallback<BookData>() {
        override fun areItemsTheSame(oldItem: BookData, newItem: BookData): Boolean = oldItem.b1Name == newItem.b1Name

        override fun areContentsTheSame(oldItem: BookData, newItem: BookData): Boolean = oldItem == newItem
    }

    inner class BookViewHolder(private val binding: ItemBookBinding) : ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                onClickItem?.invoke(getItem(adapterPosition))
            }
        }

        fun bind() {
            val data = getItem(adapterPosition)

            binding.bookImage.setImageBitmap(getItem(adapterPosition).image)
            binding.textBookName.text = data.bookName
            binding.textBookAuthor.text = data.authorName
            binding.textBookSize.text = data.bookSize

//            binding.textBookName.text = getItem(adapterPosition).bookName
//            binding.textBookAuthor.text = getItem(adapterPosition).authorName
//            binding.textBookSize.text = getItem(adapterPosition).bookSize

//            binding.animationView.setOnClickListener {
//                val animationView = binding.animationView
//                animationView.setAnimation("splash_animation.json")
//                animationView.playAnimation()
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder = BookViewHolder(
        ItemBookBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) = holder.bind()

    fun setOnClickItem(block: (BookData) -> Unit) {
        onClickItem = block
    }

    fun setOnLongClickItem(block: (BookData) -> Unit) {
        onLongClickItem = block
    }
}