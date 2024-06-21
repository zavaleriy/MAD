package com.example.mad.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mad.R
import com.example.mad.models.Feeling
import com.squareup.picasso.Picasso

class FeelingAdapter :
        RecyclerView.Adapter<FeelingAdapter.ViewHolder>() {

    private var feelings: List<Feeling> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(feelings: List<Feeling>) {
        this.feelings = feelings
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewFeeling: TextView
        val imageViewFeeling: ImageView

        init {
            textViewFeeling = view.findViewById(R.id.textViewFeeling)
            imageViewFeeling = view.findViewById(R.id.imageViewFeeling)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_feeling, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val feeling = feelings[position]
        holder.textViewFeeling.text = feeling.title
        Picasso.get()
            .load(feeling.image)
            .into(holder.imageViewFeeling)

    }

    override fun getItemCount(): Int = feelings.size

}