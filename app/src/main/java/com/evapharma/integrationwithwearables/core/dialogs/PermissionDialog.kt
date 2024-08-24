package com.evapharma.integrationwithwearables.core.dialogs

import android.content.Context
import android.content.Intent
import android.media.audiofx.BassBoost
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.evapharma.integrationwithwearables.core.BaseDialog
import com.evapharma.integrationwithwearables.databinding.DialogPermissionRationaleBinding

class PermissionDialog (context: Context) : BaseDialog<DialogPermissionRationaleBinding>(context) {

    override fun initBinding(): DialogPermissionRationaleBinding {
        return DialogPermissionRationaleBinding.inflate(layoutInflater)
    }

    override fun onDialogCreated() {

        binding.btnSettings.setOnClickListener {
            redirectToHealthConnectDownload()
        }
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
    }
    private fun redirectToHealthConnectDownload() {
        val playStoreIntent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.healthdata")
            setPackage("com.android.vending")
        }
       context.startActivity(playStoreIntent)
    }
}