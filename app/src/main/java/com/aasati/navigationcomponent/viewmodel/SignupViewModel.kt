package com.aasati.navigationcomponent.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aasati.navigationcomponent.model.LoginState
import com.aasati.navigationcomponent.model.User
import com.aasati.navigationcomponent.model.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupViewModel(private val db: UserDao) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val signupComplete = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    fun signup(username: String, password: String, info: String) {
        coroutineScope.launch {
            val user = db.getUser(username)
            if (user != null) {
                withContext(Dispatchers.Main) {
                    error.value = "User already exists"
                }
            } else {
                val user = User(username, password.hashCode(), info)
                val userId = db.insertUser(user)
                user.id = userId

                LoginState.login(user)
                withContext(Dispatchers.Main) {
                    signupComplete.value = true
                }
            }
        }
    }
}