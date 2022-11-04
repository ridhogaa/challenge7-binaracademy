package com.ergea.challengetopseven.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ergea.challengetopseven.R
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieEntity
import com.ergea.challengetopseven.databinding.MovieListFavBinding

class FavoriteMovieAdapter(private val listMovie: List<FavoriteMovieEntity>) :
    RecyclerView.Adapter<FavoriteMovieAdapter.FavMovieViewHolder>() {


    class FavMovieViewHolder(private val binding: MovieListFavBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: FavoriteMovieEntity) {
            with(itemView) {
                binding.apply {
                    titleMovie.text = movie.title
                    releaseMovie.text = movie.releaseDate
                    Glide.with(itemView.context)
                        .load("https://image.tmdb.org/t/p/w400${movie.posterPath}")
                        .into(binding.imgMovie)
                    cardView.setOnClickListener {
                        val bundle = Bundle().apply {
                            putInt("ID", movie.uniqueIdMovie!!.toInt())
                        }
                        it.findNavController()
                            .navigate(R.id.action_favoriteFragment_to_detailFragment, bundle)
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteMovieAdapter.FavMovieViewHolder =
        FavMovieViewHolder(
            MovieListFavBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FavMovieViewHolder, position: Int) =
        holder.bind(listMovie[position])


    override fun getItemCount(): Int = listMovie.size
}