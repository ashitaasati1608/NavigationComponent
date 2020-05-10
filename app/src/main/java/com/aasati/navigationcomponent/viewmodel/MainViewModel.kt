package com.aasati.navigationcomponent.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aasati.navigationcomponent.model.LoginState
import com.aasati.navigationcomponent.model.UserDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val db: UserDao) : ViewModel() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val userDeleted = MutableLiveData<Boolean>()
    val signout = MutableLiveData<Boolean>()

    fun onSignout() {
        signout.value = true
        LoginState.logout()
    }

    fun onDeleteUser() {
        coroutineScope.launch {
            LoginState.user?.let {
                db.deleteUser(it.id)
            }
            withContext(Dispatchers.Main) {
                LoginState.logout()
                userDeleted.value = true
            }
        }
    }

}