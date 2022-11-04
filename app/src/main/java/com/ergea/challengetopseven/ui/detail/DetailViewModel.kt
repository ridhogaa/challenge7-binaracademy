package com.ergea.challengetopseven.ui.detail

import androidx.lifecycle.*
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieEntity
import com.ergea.challengetopseven.data.local.datastore.DataStoreManager
import com.ergea.challengetopseven.data.remote.movie.model.GetMovieDetailResponse
import com.ergea.challengetopseven.data.repository.MovieRepository
import com.ergea.challengetopseven.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dataStoreManager: DataStoreManager,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _movie: MutableLiveData<Response<GetMovieDetailResponse>> = MutableLiveData()
    val movie: LiveData<Response<GetMovieDetailResponse>> get() = _movie

    private val _favMovie: MutableLiveData<FavoriteMovieEntity> = MutableLiveData()
    val favMovie: LiveData<FavoriteMovieEntity> get() = _favMovie

    private val _deleteMovie: MutableLiveData<FavoriteMovieEntity> = MutableLiveData()
    val deleteFavMovie: LiveData<FavoriteMovieEntity> get() = _deleteMovie

    private val _isFav: MutableLiveData<Boolean> = MutableLiveData()
    val isFav: LiveData<Boolean> get() = _isFav

    fun getDetail(id: Int) = CoroutineScope(Dispatchers.IO).launch {
        try {
            _movie.postValue(movieRepository.getMovieDetail(id))
        } catch (e: Exception) {
            throw e
        }
    }

    fun getId() =
        dataStoreManager.getId.asLiveData()

    fun isFavoriteMovie(id: Int, id_user: Int) = CoroutineScope(Dispatchers.IO).launch {
        _isFav.postValue(
            userRepository.isFavoriteMovie(id, id_user)
        )
    }

    fun addFavMovie(movie: FavoriteMovieEntity) = CoroutineScope(Dispatchers.IO).launch {
        userRepository.addFavorite(movie)
        _favMovie.postValue(movie)
    }

    fun deleteFavMovie(movie: FavoriteMovieEntity) = CoroutineScope(Dispatchers.IO).launch {
        userRepository.deleteFavorite(movie.uniqueIdMovie!!, movie.id_user!!)
        _deleteMovie.postValue(movie)
    }
}