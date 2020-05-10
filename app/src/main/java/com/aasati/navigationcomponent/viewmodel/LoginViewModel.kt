package com.aasati.navigationcomponent.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aasati.navigationcomponent.model.LoginState
import com.aasati.navigationcomponent.model.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel( val dao: UserDao) : ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val loginComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun login(username: String, password: String) {
        coroutineScope.launch {
            val user = dao.getUser(username)
            if (user != null && username.equals(user.userName) && password.hashCode().equals(user.passwordHash)) {
                withContext(Dispatchers.Main) {
                    LoginState.login(user)
                    loginComplete.value = true
                }
            } else {
                withContext(Dispatchers.Main) {
                    error.value = "User doesn't exist"
                }
            }
        }
    }
}