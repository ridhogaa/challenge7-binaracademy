package com.ergea.challengetopseven.ui.register

import androidx.lifecycle.ViewModel
import com.ergea.challengetopseven.data.local.database.user.UserEntity
import com.ergea.challengetopseven.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    var flag: Boolean = true
    fun insertUser(userEntity: UserEntity) = CoroutineScope(Dispatchers.IO).launch {
        try {
            userRepository.insertUser(userEntity)
        } catch (ex: Exception) {
            throw ex
        }
    }

    fun getIfUserExist(username: String) = CoroutineScope(Dispatchers.IO).launch {
        try {
            if (userRepository.getIfUserExist(username)) {
                flag = true
            }
            flag = false
        } catch (ex: Exception) {
            throw ex
        }
    }

}