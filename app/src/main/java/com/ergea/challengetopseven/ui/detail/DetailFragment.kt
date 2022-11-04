package com.ergea.challengetopseven.ui.detail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.ergea.challengetopseven.R
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieEntity
import com.ergea.challengetopseven.databinding.FragmentDetailBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

@AndroidEntryPoint
class DetailFragment : Fragment() {


    private val viewModel: DetailViewModel by activityViewModels()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var selectedMovie: FavoriteMovieEntity
    private var isFavorite by Delegates.notNull<Boolean>()
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        val id = arguments?.getInt("ID")
        if (id != null) {
            viewModel.getDetail(id)
            observeDetailMovie()
            setFavoriteListener()
            checkFavoriteMovie(id)
        }
    }

    private fun observeDetailMovie() {
        val baseURL = "https://image.tmdb.org/t/p/w500/"
        viewModel.movie.observe(viewLifecycleOwner) {
            binding.apply {
                if (it != null) {
                    titleMovie.text = it.body()!!.title.toString()
                    releaseDataMovie.text = it.body()!!.releaseDate.toString()
                    Glide.with(requireContext())
                        .load(baseURL + it.body()!!.backdropPath)
                        .into(binding.movieImage)
                    descMovie.text = it.body()!!.overview.toString()
                    viewModel.getId().observe(viewLifecycleOwner) { id_user ->
                        selectedMovie = FavoriteMovieEntity(
                            uniqueIdMovie = it.body()!!.id!!.toInt(),
                            title = it.body()!!.title.toString(),
                            releaseDate = it.body()!!.releaseDate.toString(),
                            posterPath = it.body()!!.posterPath,
                            id_user = id_user
                        )
                    }

                }
            }
        }
    }

    private fun setFavoriteListener() {
        binding.fabFav.apply {
            setOnClickListener {
                if (!isFavorite) {
                    addToFavorite(selectedMovie)
                    binding.fabFav.setImageResource(R.drawable.ic_love)
                    isFavorite = true
                } else {
                    deleteFromFavorite(selectedMovie)
                    binding.fabFav.setImageResource(R.drawable.ic_not_love)
                    isFavorite = false
                }
            }
        }
    }

    private fun addToFavorite(movie: FavoriteMovieEntity) {
        viewModel.addFavMovie(movie)
        viewModel.favMovie.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses tambah favorit", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Failed menambah favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun deleteFromFavorite(movie: FavoriteMovieEntity) {
        viewModel.deleteFavMovie(movie)
        viewModel.deleteFavMovie.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(requireContext(), "Sukses menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Failed menghapus favorit", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun checkFavoriteMovie(movieId: Int) {
        viewModel.getId().observe(viewLifecycleOwner) {
            viewModel.isFavoriteMovie(movieId, it)
            viewModel.isFav.observe(viewLifecycleOwner) { flag ->
                if (flag != null) {
                    if (flag) {
                        isFavorite = true
                        binding.fabFav.setImageResource(R.drawable.ic_love)
                    } else {
                        isFavorite = false
                        binding.fabFav.setImageResource(R.drawable.ic_not_love)
                    }
                } else {
                    Log.d("CHECK_FAV", "checkFavoriteMovie: ${it}")
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}