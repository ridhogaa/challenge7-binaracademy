package com.ergea.challengetopseven.ui.login

import androidx.lifecycle.*
import com.ergea.challengetopseven.data.local.database.user.UserEntity
import com.ergea.challengetopseven.data.local.datastore.DataStoreManager
import com.ergea.challengetopseven.data.remote.movie.model.ResultMovies
import com.ergea.challengetopseven.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val provideDataStoreManager: DataStoreManager
) : ViewModel() {

    private val _user = MutableLiveData<UserEntity?>()
    val user: LiveData<UserEntity?> get() = _user
    fun flag(email: String, password: String) =
        CoroutineScope(Dispatchers.IO).launch {
            if (userRepository.validateUserLogin(email, password) != null)
                _user.postValue(userRepository.validateUserLogin(email, password))
            else
                _user.postValue(null)
        }

    fun setIsLogin(status: Boolean) {
        viewModelScope.launch {
            provideDataStoreManager.setIsLogin(status)
        }
    }

    fun setId(id: Int) {
        viewModelScope.launch {
            provideDataStoreManager.setId(id)
        }
    }

    fun setUsername(username: String) {
        viewModelScope.launch {
            provideDataStoreManager.setUsername(username)
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return provideDataStoreManager.getIsLogin.asLiveData()
    }

}