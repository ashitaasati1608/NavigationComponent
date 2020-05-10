package com.aasati.navigationcomponent.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.aasati.navigationcomponent.R
import com.aasati.navigationcomponent.model.LoginState
import com.aasati.navigationcomponent.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.android.ext.android.inject

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBtn.setOnClickListener { onLogin(it) }
        gotoSignupBtn.setOnClickListener { onGotoSignup(it) }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.loginComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            if (isComplete) {
                Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show()
                LoginState.isLoggedIn = true
                val action = LoginFragmentDirections.actionGoToMain()
                Navigation.findNavController(loginUsername).navigate(action)
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, "User doesn't exist + $error", Toast.LENGTH_SHORT).show()
            LoginState.isLoggedIn = false
        })
    }

    private fun onLogin(v: View) {
        val username = loginUsername.text.toString()
        val password = loginPassword.text.toString()
        if (username.isNullOrEmpty() || password.isNullOrEmpty()) {
            Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.login(username, password)
        }
    }

    private fun onGotoSignup(v: View) {
        val action = LoginFragmentDirections.actionGoToSignup()
        Navigation.findNavController(v).navigate(action)
    }
}
