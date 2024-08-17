package com.evapharma.integrationwithwearables

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.evapharma.integrationwithwearables.core.BaseActivity
import com.evapharma.integrationwithwearables.databinding.ActivityMainBinding
import com.evapharma.integrationwithwearables.features.covid_cases.presentation.CovidCasesActions
import com.evapharma.integrationwithwearables.features.covid_cases.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onActivityCreated() {
/*
        binding.retrieveCasesBtn.setOnClickListener {
            Log.e("TAG", "onClick: ")
            viewModel.executeAction(
                CovidCasesActions.GetCovidCases
            )
            handleCovidCasesViewState()
        }*/

    }

    private fun handleCovidCasesViewState() {
        this.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.viewStates.collect { viewState ->
                    if (viewState.isLoading) {
                        showLoading()
                    }
                    Log.d("TAG", "handleCovidCasesViewState: ${viewState.data} ")
                    if (viewState.data != null) {
                        hideLoading()
                        viewState.data.let {
                        //    binding.casesNo.text = it.data?.local_new_cases.toString()
                        }
                    } else {
                        hideLoading()
                        viewState.error?.let {
                        //    binding.casesNo.text = it.message
                        }
                    }
                }
            }
        }
    }
}