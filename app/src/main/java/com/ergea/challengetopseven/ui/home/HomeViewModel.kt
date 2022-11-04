package com.ergea.challengetopseven.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ergea.challengetopseven.data.local.database.user.UserEntity
import com.ergea.challengetopseven.data.local.datastore.DataStoreManager
import com.ergea.challengetopseven.data.remote.movie.model.ResultMovies
import com.ergea.challengetopseven.data.repository.MovieRepository
import com.ergea.challengetopseven.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {

    init {
        getPopular()
    }

    private val _movie = MutableLiveData<List<ResultMovies>>()
    val movie: LiveData<List<ResultMovies>> get() = _movie

    private fun getPopular() = CoroutineScope(Dispatchers.IO).launch {
        try {
            _movie.postValue(movieRepository.getMovieList().body()!!.results)
        } catch (e: Exception) {
            throw e
        }
    }

    fun getDataStoreUsername(): LiveData<String> {
        return dataStoreManager.getUsername.asLiveData()
    }

}