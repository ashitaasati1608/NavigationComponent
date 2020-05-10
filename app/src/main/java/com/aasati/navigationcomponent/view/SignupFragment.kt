package com.aasati.navigationcomponent.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.aasati.navigationcomponent.R
import com.aasati.navigationcomponent.viewmodel.SignupViewModel
import kotlinx.android.synthetic.main.fragment_signup.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignupFragment : Fragment() {
    private val viewModel by viewModel<SignupViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signupBtn.setOnClickListener { onSignup() }
        gotoLoginBtn.setOnClickListener { onGotoLogin(it) }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signupComplete.observe(viewLifecycleOwner, Observer { isComplete ->
            if (isComplete) {
                Toast.makeText(context, "User created successfully", Toast.LENGTH_SHORT).show()
                val bundle = bundleOf("username" to signupUsername.text.toString())
                Navigation.findNavController(signupUsername).navigate(R.id.actionGoToMain, bundle)
            } else {
                Toast.makeText(context, "oops!! something went wrong", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.error.observe(viewLifecycleOwner, Observer { error ->
            Toast.makeText(context, "oops!! something went wrong : $error", Toast.LENGTH_SHORT)
                .show()
        })
    }

    private fun onGotoLogin(v: View) {
        val action = SignupFragmentDirections.actionGoToLogin()
        Navigation.findNavController(v).navigate(action)
    }

    private fun onSignup() {
        val username = signupUsername.text.toString()
        val password = signupPassword.text.toString()
        val info = otherInfo.text.toString()

        if (username.isEmpty() || password.isEmpty() || info.isEmpty()) {
            Toast.makeText(context, "Please enter all the data", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.signup(username, password, info)
        }
    }

}
