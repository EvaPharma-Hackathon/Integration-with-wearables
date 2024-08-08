package com.evapharma.integrationwithwearables.core

import android.content.Context
import android.view.WindowManager
import com.evapharma.integrationwithwearables.databinding.DialogProgressLayoutBinding

open class ProgressDialog(context: Context) : BaseDialog<DialogProgressLayoutBinding>(context) {

    override fun initBinding(): DialogProgressLayoutBinding {
        return DialogProgressLayoutBinding.inflate(layoutInflater)
    }

    override fun onDialogCreated() {
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT
        )
    }
}


class OptionalDismissableProgressDialog(context: Context) : ProgressDialog(context) {
    fun show(isCancelable: Boolean, onDismissAction: (() -> Unit)? = null) {
        setCancelable(isCancelable)
        onDismissAction?.let { dismissAction ->
            setOnDismissListener {
                dismissAction.invoke()
            }
        }

        show()
    }
}