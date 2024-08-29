package com.evapharma.integrationwithwearables.core.dialogs

import android.content.Context
import com.evapharma.integrationwithwearables.core.BaseDialog
import com.evapharma.integrationwithwearables.databinding.ConfirmDialogBinding

class ConfirmationDialog (context: Context) : BaseDialog<ConfirmDialogBinding>(context) {
    override fun initBinding(): ConfirmDialogBinding {
        return ConfirmDialogBinding.inflate(layoutInflater)
    }

    override fun onDialogCreated() {
        binding.dismissButton.setOnClickListener {
            dismiss()
        }
    }
}