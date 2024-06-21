package com.example.mad.Adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.R

class GalleryAdapter(
    private val click : (Uri) -> Unit,
    private val addClick : () -> Unit
) : ListAdapter<Uri, RecyclerView.ViewHolder>(ImageViewCallback) {

    companion object {
        private const val TYPE_IMAGE = 0
        private const val TYPE_ADD_BUTTON = 1
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_elem)
    }

    class AddViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val addButton: ImageView = view.findViewById(R.id.add_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_IMAGE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_item, parent, false)
            ViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.gallery_add, parent, false)
            AddViewHolder(view)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < currentList.size) TYPE_IMAGE else TYPE_ADD_BUTTON
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == TYPE_IMAGE) {
            (holder as ViewHolder).imageView.setImageURI(getItem(position))
            holder.imageView.setOnClickListener {
                click(getItem(position))
            }
        } else {
            (holder as AddViewHolder).addButton.setOnClickListener {
                addClick()
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size + 1
    }

    object ImageViewCallback: DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

    }

}