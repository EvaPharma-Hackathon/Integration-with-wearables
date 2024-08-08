package com.evapharma.integrationwithwearables.core

interface DismissableProgressDialog {

    fun showLoading(
        progressDialog: OptionalDismissableProgressDialog,
        isCancelable: Boolean,
        onDismissAction: (() -> Unit)? = null
    )
}

class DismissableProgressDialogImpl : DismissableProgressDialog {
    override fun showLoading(
        progressDialog: OptionalDismissableProgressDialog,
        isCancelable: Boolean,
        onDismissAction: (() -> Unit)?
    ) {
        progressDialog.show(isCancelable, onDismissAction)
    }

}