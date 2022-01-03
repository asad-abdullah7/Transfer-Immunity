package com.arhamsoft.online.transferimunityclone.utils.functionsCommon

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.arhamsoft.online.transferimunityclone.R
import com.arhamsoft.online.transferimunityclone.activities.LoginActivity
import com.arhamsoft.online.transferimunityclone.activities.UploadingFilesListActivity
import com.arhamsoft.online.transferimunityclone.databinding.CustomAlertLayoutBinding

class CommonFunctions {

    companion object {

        private lateinit var binding: CustomAlertLayoutBinding
        private lateinit var builder: AlertDialog.Builder
        private lateinit var dialog: AlertDialog
        private lateinit var handler: Handler

        fun showDialog(
            activity: Activity,
            title: String,
            message: String,
            intentCheck: Int
        ) {

            handler = Handler(Looper.getMainLooper())
            builder = AlertDialog.Builder(activity)
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(activity),
                R.layout.custom_alert_layout,
                null,
                false
            )
            builder.setView(binding.root)
            if (intentCheck == 2) {
                binding.btnViewCustom.visibility = View.VISIBLE
                binding.btnOkCustom.visibility = View.VISIBLE
                binding.btnYesCustom.visibility = View.GONE
            } else {
                binding.btnViewCustom.visibility = View.GONE
                binding.btnOkCustom.visibility = View.GONE
                binding.btnYesCustom.visibility = View.VISIBLE
            }

            builder.setCancelable(false)
            binding.btnYesCustom.text = "OK"
            binding.txtDialogTitle.text = title
            binding.txtDialogMessage.text = message
            binding.btnYesCustom.setOnClickListener {

                binding.btnYesCustom.isEnabled = false
                if (intentCheck == 1) {
                    dialog.dismiss()
                } else {
                    val intent = Intent(activity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    activity.startActivity(intent)
                    activity.finish()
                    handler.postDelayed(
                        {
                            binding.btnYesCustom.isEnabled = true
                        },
                        1000,
                    )
                    dialog.dismiss()
                }
            }

            binding.btnOkCustom.setOnClickListener {
                dialog.dismiss()
            }

            binding.btnViewCustom.setOnClickListener {
                val i = Intent(activity, UploadingFilesListActivity::class.java)
                activity.startActivity(i)
                dialog.dismiss()
            }
            dialog = builder.create()

            dialog.show()

        }

    }
}