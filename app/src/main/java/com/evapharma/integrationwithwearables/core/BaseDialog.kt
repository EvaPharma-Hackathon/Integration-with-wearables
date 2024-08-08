package com.evapharma.integrationwithwearables.core

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.viewbinding.ViewBinding

abstract class BaseDialog<V : ViewBinding>(context: Context) : Dialog(context) {

    lateinit var binding: V

    abstract fun initBinding(): V

    abstract fun onDialogCreated()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = initBinding()
        setContentView(binding.root)
        window!!.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 40))
        window!!.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        if (LocaleHelper.getLanguage(this.context).equals(LanguageType.ARABIC))
            window!!.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        else window!!.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR

        
        onDialogCreated()
    }
}