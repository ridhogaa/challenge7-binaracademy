package com.ergea.challengetopseven.ui.profile

import androidx.lifecycle.*
import com.ergea.challengetopseven.data.local.database.movie.FavoriteMovieEntity
import com.ergea.challengetopseven.data.local.database.user.UserEntity
import com.ergea.challengetopseven.data.local.datastore.DataStoreManager
import com.ergea.challengetopseven.data.repository.UserRepository
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val userRepository: UserRepository
) :
    ViewModel() {

    private val _user: MutableLiveData<UserEntity> = MutableLiveData()
    val user: LiveData<UserEntity> get() = _user

    fun getUserById(id_user: Int) = CoroutineScope(Dispatchers.IO).launch {
        _user.postValue(userRepository.getUserById(id_user))
    }

    fun updateUser(
        userId: Int,
        username: String,
        fullname: String,
        tanggalLahir: String,
        alamat: String,
        gambar: String
    ) = CoroutineScope(Dispatchers.IO).launch {
        userRepository.updateUser(userId, username, fullname, tanggalLahir, alamat, gambar)
        dataStoreManager.setUsername(username)
    }

    fun removeIsLogin() =
        viewModelScope.launch {
            dataStoreManager.removeIsLogin()
        }


    fun removeId() =
        viewModelScope.launch {
            dataStoreManager.removeId()
        }


    fun removeUsername() =
        viewModelScope.launch {
            dataStoreManager.removeUsername()
        }


    fun getId() =
        dataStoreManager.getId.asLiveData()
}