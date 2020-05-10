package com.aasati.navigationcomponent.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.aasati.navigationcomponent.R
import com.aasati.navigationcomponent.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signoutBtn.setOnClickListener { onSignout() }
        usernameTV.text = "Hello " + arguments?.getString("username")

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signout.observe(viewLifecycleOwner, Observer {
            goToSignUpScreen()
        })
    }

    private fun onSignout() {
        viewModel.onSignout()
    }

    private fun goToSignUpScreen() {
        val action = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(usernameTV).navigate(action)
    }

}
