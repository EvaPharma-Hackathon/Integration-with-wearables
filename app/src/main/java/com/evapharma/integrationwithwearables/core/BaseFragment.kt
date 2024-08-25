package com.evapharma.integrationwithwearables.core

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.evapharma.integrationwithwearables.core.dialogs.ErrorDialog
import com.evapharma.integrationwithwearables.core.dialogs.SessionExpiredDialog
import com.evapharma.integrationwithwearables.core.models.ErrorResponse
import com.evapharma.integrationwithwearables.core.models.Exceptions
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.launch


abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel>
    : Fragment(), BaseView, DismissableProgressDialog by DismissableProgressDialogImpl() {

    private var _binding: V? = null
    val binding get() = _binding!!

    open lateinit var viewModel: VM

    var progressDialog: OptionalDismissableProgressDialog? = null
    private lateinit var sessionExpiredDialog: SessionExpiredDialog

    abstract fun initBinding(): V

    abstract fun initViewModel()

    abstract fun onFragmentCreated()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreate(savedInstanceState)

        if (_binding == null) {
            initViewModel()
            _binding = initBinding()

            if (progressDialog == null && context != null)
                initProgressDialog()

            viewModel.errorLiveData.observe(viewLifecycleOwner, this::onError)

            handleExceptions()
            onFragmentCreated()
        }
        return binding.root
    }

    private fun handleExceptions() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.errorStateFlow.collect { error ->
                    hideLoading()
                    when (error) {
                        is Exceptions.Unauthorized -> onSessionExpired()
                        is Exceptions.InternalServerError -> ErrorDialog(requireContext()).show()
                        is Exceptions.ErrorResponse -> {
                            showCustomError(error)
                        }
                    }
                }
            }
        }
    }

    open fun showCustomError(error: Exceptions.ErrorResponse) {
        showError(error)
    }

    open fun showError(error: Exceptions.ErrorResponse) {
        showErrorMsg(msg = error.msg)
    }

    override fun onResume() {
        super.onResume()
        firebaseLogScreen(this.javaClass.simpleName)
    }

    override fun onError(error: ErrorResponse?) {
        if (activity != null && isAdded) {
            hideLoading()
            error?.let {
                when (error.code) {
                    500 -> ErrorDialog(requireContext()).show()
                    401 -> onSessionExpired()
                    else -> {
                        if (error.errorList.isNotEmpty()) {

                            error.errorList[0].message?.let { msg ->

                                showErrorMsg(msg)
                            }
                        } else
                            showErrorMsg(it.toString())
                    }
                }
                viewModel.errorLiveData.postValue(null)
            }
        }

    }

    fun initProgressDialog() {
        progressDialog = OptionalDismissableProgressDialog(requireContext())
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun hideLoading() {
        if (progressDialog == null)
            initProgressDialog()
        progressDialog?.dismiss()
    }

    override fun onDestroyView() {
        hideLoading()
        super.onDestroyView()
    }

    override fun onDestroy() {
        hideLoading()
        _binding = null
        super.onDestroy()
    }

    fun firebaseLogScreen(screenClassName: String) {

        val screenName = when (screenClassName) {

            "ServiceFragment" -> "Services"
            "DoctorListFragment" -> "CertainSpeciality"
            "DoctorDetailFragment" -> "CertainDoctor"
            "BookingStepOneFragment" -> "BookAppointment"
            "TestsFragment" -> "LabTests"
            "CovidFragment" -> "Covid19Tests"
            "HomeFragment" -> "HomePage"
            "EmergencyFragment" -> "EmergencyHomePage"
            "SpecialityFragment" -> "SpecialitiesList"
            "LoginFragment" -> "Login"
            "RegisterFragment" -> "CreateAccount"
            "BookingsFragment" -> "MyBookings"
            "SetupAccountFragment" -> "MyProfileSetup"
            "PspLandingPageFragment" -> "PSP_Landing_Page"
            "CancelPSP_Popup" -> "CancelPSP_Popup"
            "ProgramDetailsFragment" -> "PSP_Program_Details"
            "SubscribeToProgramFragment" -> ""
            "PSP_PatientForm" -> "PSP_PatientForm"
            "PSP_CaregiverForm" -> "PSP_CaregiverForm"
            "PSP_Subscription_Options" -> "PSP_Subscription_Options"
            "PSP_Profile_Setup" -> "PSP_Profile_Setup"
            "PSP_SubscriptionConfirmation" -> "PSP_SubscriptionConfirmation"
            "RequestDetailFragment" -> "Request Details"
            "MyRequestHistoryFragment" -> "My Requests History"

            else -> ""
        }
    }

    fun returnError(error: String): Boolean {
        showErrorMsg(error)
        return false
    }

    override fun showSuccessMsg(msg: String) {
        if (context != null)
            Toasty.success(requireContext(), msg, Toast.LENGTH_LONG, false).show()
    }

    override fun showErrorMsg(msg: String) {
        if (context != null)
            Toasty.error(requireContext(), msg, Toast.LENGTH_LONG, false).show()
    }

    fun showKeyboard() {
        val imm =
            requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hideKeyboard() {
        try {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    protected fun onSessionExpired() {
        sessionExpiredDialog.show()
    }

    fun resetBinding() {
        _binding = null
    }


}