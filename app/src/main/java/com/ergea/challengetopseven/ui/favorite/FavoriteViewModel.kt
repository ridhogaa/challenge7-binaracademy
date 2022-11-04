package com.ergea.challengetopseven.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieEntity
import com.ergea.challengetopseven.data.local.datastore.DataStoreManager
import com.ergea.challengetopseven.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStoreManager: DataStoreManager
) :
    ViewModel() {

    private val _listMovie: MutableLiveData<List<FavoriteMovieEntity>> = MutableLiveData()
    val listMovie: LiveData<List<FavoriteMovieEntity>> get() = _listMovie

    fun getAllFavoriteMovie(id_user: Int) = CoroutineScope(Dispatchers.IO).launch {
        _listMovie.postValue(userRepository.getFavoriteMovies(id_user))
    }

    fun getId() =
        dataStoreManager.getId.asLiveData()
}