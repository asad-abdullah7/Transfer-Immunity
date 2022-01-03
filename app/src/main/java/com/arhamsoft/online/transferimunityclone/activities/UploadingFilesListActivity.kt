package com.arhamsoft.online.transferimunityclone.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.arhamsoft.online.transferimunityclone.R
import com.arhamsoft.online.transferimunityclone.adapters.QueueSendingEmailAdapter
import com.arhamsoft.online.transferimunityclone.databinding.ActivityUploadingFilesListBinding
import com.arhamsoft.online.transferimunityclone.models.fileModel.FileModelRoom
import com.arhamsoft.online.transferimunityclone.roomDataBase.AppDataBase
import com.arhamsoft.online.transferimunityclone.utils.functionsCommon.CommonFunctions

class UploadingFilesListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadingFilesListBinding
    private var listFileModelRoom: List<FileModelRoom> = ArrayList()
    private lateinit var handler: Handler
    private lateinit var adapter: QueueSendingEmailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadingFilesListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
    }

    private lateinit var runnable: Runnable

    private fun setHandler() {
        handler = Handler(Looper.getMainLooper())

        runnable = Runnable {
            try {
                val thread = Thread {
                    listFileModelRoom = AppDataBase.getAppDB(this)?.fileDAO()?.getAllFiles()!!
                    runOnUiThread {
                        if (listFileModelRoom.isNotEmpty()) {

                            updateData(listFileModelRoom)
                            adapter.updateList(listFileModelRoom)
                        } else {
                            binding.noRecordAvailable.visibility = View.VISIBLE
                            binding.sendingLayout.visibility = View.GONE
                        }
                    }
                }
                thread.start()
                thread.join()
            } finally {
                handler.postDelayed(runnable, 3000)
            }
        }
        handler.post(runnable)
    }

    private fun updateData(listFile: List<FileModelRoom>) {

        binding.sendingLayout.visibility = View.VISIBLE
        binding.noRecordAvailable.visibility = View.GONE

        binding.sendingLayout.layoutManager = LinearLayoutManager(this)
        adapter = QueueSendingEmailAdapter(this, listFile, object : QueueSendingEmailAdapter.OnCloseImageClickListener{
            override fun onCloseImageClickListener(fileModelRoom: FileModelRoom) {
            val thread = Thread {
                AppDataBase.getAppDB(this@UploadingFilesListActivity)?.fileDAO()?.deleteFile(fileModelRoom)
                AndroidNetworking.cancel("file")
            }
            thread.start()
            thread.join()
            }
        })
        binding.sendingLayout.adapter = adapter
    }

    private fun setListeners() {
        //onBackPressed
        binding.back.setOnClickListener {
            onBackPressed()
        }

        //Info Click
        binding.info.setOnClickListener {
            CommonFunctions.showDialog(this, "Disclaimer", getString(R.string.info_text), 1)
        }


    }

    override fun onResume() {
        super.onResume()
        setHandler()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}