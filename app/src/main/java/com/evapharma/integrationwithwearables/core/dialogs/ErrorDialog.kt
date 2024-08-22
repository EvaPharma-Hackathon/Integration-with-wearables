package com.evapharma.integrationwithwearables.core.dialogs

import android.content.Context
import com.evapharma.integrationwithwearables.R
import com.evapharma.integrationwithwearables.core.BaseDialog
import com.evapharma.integrationwithwearables.databinding.ErrorDialogLayoutBinding

class ErrorDialog(context: Context) : BaseDialog<ErrorDialogLayoutBinding>(context) {

     var message: String = R.string.warning_desc.toString()
    override fun initBinding(): ErrorDialogLayoutBinding {
        return ErrorDialogLayoutBinding.inflate(layoutInflater)
    }

    override fun onDialogCreated() {
        binding.txtdesc.text = message
        binding.btnOK.setOnClickListener {
            dismiss()
        }
    }


}
