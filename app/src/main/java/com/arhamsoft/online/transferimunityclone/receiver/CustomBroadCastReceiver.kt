package com.arhamsoft.online.transferimunityclone.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.arhamsoft.online.transferimunityclone.utils.sharedPreferences.CustomSharedPref
import com.arhamsoft.online.transferimunityclone.workManager.AttachmentUploadAndSendEmailWorkManager


class CustomBroadCastReceiver : BroadcastReceiver() {
    private lateinit var sharedPreferences: CustomSharedPref
    override fun onReceive(context: Context?, intent: Intent?) {
        sharedPreferences = CustomSharedPref(context!!)

        if (intent?.action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (isNetworkInterfaceAvailable(context)) {

                Toast.makeText(context, "Internet Connected", Toast.LENGTH_SHORT).show()

                // save value too
                sharedPreferences.saveUploading("UP", false)


                // Start WorkManager Service
                startWorkManager(context)

            } else {
                Toast.makeText(context, "Internet not Connected", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun isNetworkInterfaceAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    // Start WorkManager Service
    private fun startWorkManager(context: Context) {

        Toast.makeText(context, "Service Called", Toast.LENGTH_SHORT).show()

        val builder =
            OneTimeWorkRequest.Builder(AttachmentUploadAndSendEmailWorkManager::class.java)

        val request = builder.build()
        WorkManager.getInstance(
            context
        ).enqueue(request)

    }

}