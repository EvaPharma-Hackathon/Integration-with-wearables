package com.evapharma.integrationwithwearables.features.vitals_data.presentation.view.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.evapharma.integrationwithwearables.R
import com.evapharma.integrationwithwearables.core.dialogs.ErrorDialog
import com.evapharma.integrationwithwearables.databinding.FragmentLoginBinding
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.LoginRequest
import com.evapharma.integrationwithwearables.features.vitals_data.presentation.viewmodels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater , container , false)
        return binding.root
    }
     private fun initViewModel() {
         viewModel = ViewModelProvider(requireActivity())[LoginViewModel::class.java]
     }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        login()
    }

    private fun login(){
        binding.loginButton.setOnClickListener {
            observeLoginState()
        }
    }
    private fun observeLoginState(){
        val phone = LoginRequest(binding.loginInput.text.toString())
        viewModel.resetUiState()
        viewModel.login(phone)
        lifecycleScope.launch {
             viewModel.uiState.collect{ state->
                 when(state){
                     is ViewState.Loading -> binding.progressBar2.visibility = View.VISIBLE
                     is ViewState.Success -> {
                         binding.progressBar2.visibility = View.GONE
                         val navController = Navigation.findNavController(requireView())
                         if (navController.currentDestination?.id == R.id.loginFragment) {
                             navController.navigate(R.id.action_loginFragment_to_vitalsFragment)
                         } else {
                             Log.e("LoginFragment", "Cannot navigate, not on LoginFragment")
                         }
                     }
                     is ViewState.Error -> {
                         binding.progressBar2.visibility = View.GONE
                         showErrorDialog()
                     }
                 }
             }
        }
    }
    private fun showErrorDialog() {
        val errorDialog = ErrorDialog(requireContext())
        errorDialog.message = "Failed to login"
        errorDialog.show()
    }
}
