package com.evapharma.integrationwithwearables.core

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.evapharma.integrationwithwearables.core.dialogs.ErrorDialog
import com.evapharma.integrationwithwearables.core.models.ErrorResponse
import es.dmoral.toasty.Toasty

abstract class BaseActivity<V : ViewBinding, VM : BaseViewModel> : AppCompatActivity(), BaseView,
    DismissableProgressDialog by DismissableProgressDialogImpl() {

    open lateinit var binding: V
    open lateinit var viewModel: VM

    var progressDialog: OptionalDismissableProgressDialog? = null

    abstract fun initBinding(): V

    abstract fun initViewModel()

    abstract fun onActivityCreated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocaleHelper.setLocale(this, LocaleHelper.getLanguage(this) ?: "en")
        LocaleHelper.overrideLocale(applicationContext)

        initViewModel()
        binding = initBinding()
        setContentView(binding.root)

        progressDialog = OptionalDismissableProgressDialog(this)

        viewModel.errorLiveData.observe(this, this::onError)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        onActivityCreated()
    }

    override fun onResume() {
        super.onResume()
        firebaseLogScreen(this.javaClass.simpleName)
    }

    override fun onError(error: ErrorResponse?) {
        hideLoading()
        error?.let {
            when (error.code) {
                500 -> ErrorDialog(this).show()
//                401 -> onSessionExpired()
                else -> showErrorMsg(it.toString())
            }
            viewModel.errorLiveData.postValue(null)
        }
    }

    override fun showLoading() {
        progressDialog?.show()
    }

    override fun hideLoading() {
        progressDialog?.dismiss()
    }

    override fun showSuccessMsg(msg: String) {
        Toasty.success(this, msg, Toast.LENGTH_LONG, false).show()
    }

    override fun showErrorMsg(msg: String) {
        Toasty.error(this, msg, Toast.LENGTH_LONG, false).show()
    }

    private fun firebaseLogScreen(screenClassName: String) {

        val screenName = when (screenClassName) {

            "MyEMRActivity" -> "EMR"
            "PersonalInfoActivity" -> "EMR_PersonalInfo"
            "MedicalTestsActivity" -> "EMR_MedicalTests"
            "MedicationsActivity" -> "EMR_Medications"
            "AppSettingsActivity" -> "AppSettings"
            "ChangePasswordActivity" -> "ChangePassword"

            else -> ""
        }
    }

    fun returnError(error: String): Boolean {
        showErrorMsg(error)
        return false
    }

    fun showKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY)
    }

    fun hideKeyboard() {
        try {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(LocaleHelper.wrapContext(newBase))
    }

}