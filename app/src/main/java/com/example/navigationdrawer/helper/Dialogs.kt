package com.example.navigationdrawer.helper

import android.content.Context
import android.content.DialogInterface
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.example.navigationdrawer.R


object Dialogs {
    fun dialogYesNo(mContext: Context, title: String, message: String, positiveButtonText: String, negativeButtonText: String, actionListener: DialogActionListener) {
        val builder = AlertDialog.Builder(mContext, R.style.MyDialogTheme)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButtonText
        ) { dialog, which ->
            // positive button logic
            dialog.dismiss()
            actionListener.onYesClick()
        }
        if (negativeButtonText != "") {
            builder.setNegativeButton(negativeButtonText,
                    object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {
                            // negative button logic
                            dialog.dismiss()
                            actionListener.onNoClick()
                        }
                    })
        }

        val dialog = builder.create()
        // display dialog
        dialog.show()
        if (negativeButtonText != "") {
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(AppUtilities.fetchColorResource(mContext, R.color.colorRed))
        }
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(AppUtilities.fetchColorResource(mContext, R.color.colorGreen))
        dialog.setCancelable(false)
        doKeepDialog(dialog)
    }

    fun dialogOk(mContext: Context, title: String, message: String, positiveButtonText: String) {
        val builder = AlertDialog.Builder(mContext, R.style.MyDialogTheme)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveButtonText
        ) { dialog, which ->
            // positive button logic
            dialog.dismiss()
        }
        val dialog = builder.create()
        // display dialog
        dialog.show()
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(AppUtilities.fetchColorResource(mContext, R.color.colorGreen))
        dialog.setCancelable(false)
        doKeepDialog(dialog)
    }

    /*fun dialogWithEdittextAndTwoButtons(mContext: Context, title: String,
                                        hintText: String, etType: String,
                                        positiveButtonText: String, negativeButtonText: String,
                                        actionListener: DialogActionWithValueListener, invoiceBalance: String) {
        val builder = AlertDialog.Builder(mContext, R.style.MyDialogTheme)
        builder.setTitle(title)
        // Set up the input
        val view = LayoutInflater.from(mContext).inflate(R.layout.edittext_forgot_pwd_email, null)
        // Set up the input
        val til_forgot_password_email = view.findViewById(R.id.til_forgot_password_email) as TextInputLayout
        val input = view.findViewById(R.id.forgot_password_email) as TextInputEditText
        til_forgot_password_email.hint = hintText
        if (etType == "email") {
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        } else if (etType == "name") {
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        } else if (etType == "textPassword") {
            input.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        } else if (etType == "amount") {
            input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_NUMBER_FLAG_SIGNED
            input.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(2, 0.0, 1000000.0))
        }
        // Specify the typePackage of input expected; this, for example, sets the input as a password, and will mask the text
        builder.setView(view)
        builder.setPositiveButton(positiveButtonText, null)
        builder.setNegativeButton(negativeButtonText, null)

        val alDialog = builder.create()
        alDialog.setOnShowListener {
            alDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                // positive button logic
                til_forgot_password_email.isErrorEnabled = false
                if (etType == "email") {
                    if (!input.text.toString().trim { it <= ' ' }.isEmpty() && AppUtilities.isValidEmail(input.text.toString().trim { it <= ' ' })) {
                        if (AppUtilities.isOnline(mContext)) {
                            alDialog.dismiss()
                            actionListener.onYesClick(input.text.toString())
                        } else {
                            AppUtilities.showSnackBar(view, mContext.resources.getString(R.string.network_check), Snackbar.LENGTH_SHORT)
                        }

                    } else {
                        if (input.text.toString().trim { it <= ' ' }.isEmpty()) {
                            til_forgot_password_email.isErrorEnabled = true
                            til_forgot_password_email.error = "Enter email address"
                        } else if (!AppUtilities.isValidEmail(input.text.toString().trim { it <= ' ' })) {
                            til_forgot_password_email.isErrorEnabled = true
                            til_forgot_password_email.error = "Enter a valid email address"
                        }
                    }
                } else if (etType == "textPassword") {
                    if (!input.text.toString().trim().isEmpty()) {
                        if (AppUtilities.isOnline(mContext)) {
                            alDialog.dismiss()
                            actionListener.onYesClick(input.text.toString())
                        } else {
                            AppUtilities.showSnackBar(view, mContext.resources.getString(R.string.network_check), Snackbar.LENGTH_SHORT)
                        }

                    } else {
                        if (input.text.toString().trim { it <= ' ' }.isEmpty()) {
                            til_forgot_password_email.isErrorEnabled = true
                            til_forgot_password_email.error = "Enter Password"
                        }
                    }
                } else if (etType == "amount") {
                    if (!input.text.toString().trim().isEmpty()
                            && input.text.toString().toDouble() > 0.0
                            && input.text.toString().toDouble() <= invoiceBalance.toDouble()) {
                        alDialog.dismiss()
                        actionListener.onYesClick(input.text.toString())

                    } else {
                        if (input.text.toString().trim().isEmpty()) {
                            til_forgot_password_email.isErrorEnabled = true
                            til_forgot_password_email.error = "Enter the amount!"
                        } else if (input.text.toString().toDouble() < 0.0) {
                            til_forgot_password_email.isErrorEnabled = true
                            til_forgot_password_email.error = "Negative values not allowed!"
                        } else if (input.text.toString().toDouble() > invoiceBalance.toDouble()) {
                            til_forgot_password_email.isErrorEnabled = true
                            til_forgot_password_email.error = "Entered amount exceeds the invoice balance!"
                        }
                    }
                }
            }

            alDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener {
                // negativ button logic
                alDialog.dismiss()
                actionListener.onNoClick()
            }
        }
        // display dialog
        alDialog.show()
        alDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#FF0000"))
        alDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#650E77"))
        doKeepDialog(alDialog)

    }*/

    private fun doKeepDialog(dialog: AlertDialog?) {
        val lp = WindowManager.LayoutParams()
        lp.copyFrom(dialog!!.window!!.attributes)
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = lp
    }

    interface DialogActionListener {
        fun onYesClick()
        fun onNoClick()
    }

    interface DialogActionWithValueListener {
        fun onYesClick(value: String)
        fun onNoClick()
    }
}