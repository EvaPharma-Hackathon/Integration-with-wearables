package com.evapharma.integrationwithwearables.core.dialogs

import android.content.Context
import com.evapharma.integrationwithwearables.core.BaseDialog
import com.evapharma.integrationwithwearables.databinding.ErrorDialogLayoutBinding

class ErrorDialog(context: Context) : BaseDialog<ErrorDialogLayoutBinding>(context) {
    override fun initBinding(): ErrorDialogLayoutBinding {
        return ErrorDialogLayoutBinding.inflate(layoutInflater)
    }

    override fun onDialogCreated() {
        binding.btnOK.setOnClickListener {
            dismiss()
        }
    }
}