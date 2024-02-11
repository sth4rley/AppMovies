package com.example.myapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.R
import com.example.myapplication.entities.Movie

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;


class ItemAdapter (private var items: List<Movie>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val moviePoster: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load("https://image.tmdb.org/t/p/w342/" + items[position].poster_path)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
            .into(holder.moviePoster)

        val currentItem = items[position]

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(currentItem)
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
    }

    private var onItemClickListener: OnItemClickListener? = null


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }



}