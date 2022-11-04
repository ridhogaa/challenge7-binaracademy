package com.ergea.challengetopseven.ui.favorite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ergea.challengetopseven.R
import com.ergea.challengetopseven.adapter.FavoriteMovieAdapter
import com.ergea.challengetopseven.databinding.FragmentFavoriteBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val viewModel: FavoriteViewModel by activityViewModels()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        getAllFavMovies()
    }

    private fun getAllFavMovies() {
        viewModel.getId().observe(viewLifecycleOwner) {
            viewModel.getAllFavoriteMovie(it)
        }
        viewModel.listMovie.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.favoriteReyclerView.layoutManager = LinearLayoutManager(requireContext())
                binding.favoriteReyclerView.setHasFixedSize(false)
                binding.favoriteReyclerView.adapter = FavoriteMovieAdapter(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}