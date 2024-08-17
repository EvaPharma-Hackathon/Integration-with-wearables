package com.evapharma.integrationwithwearables.core.dialogs

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.view.View
import android.widget.Button
import androidx.viewbinding.ViewBinding
import com.evapharma.integrationwithwearables.core.BaseDialog
import com.evapharma.integrationwithwearables.databinding.DialogPermissionRationaleBinding

class PermissionRationaleDialog(context: Context) : BaseDialog<DialogPermissionRationaleBinding>(context) {

    override fun initBinding(): DialogPermissionRationaleBinding {
        return DialogPermissionRationaleBinding.inflate(layoutInflater)
    }

    override fun onDialogCreated() {
        binding.btnSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = android.net.Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            context.startActivity(intent)
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
}
