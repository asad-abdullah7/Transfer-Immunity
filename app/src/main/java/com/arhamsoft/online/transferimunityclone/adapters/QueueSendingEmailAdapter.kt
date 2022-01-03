package com.arhamsoft.online.transferimunityclone.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.online.transferimunityclone.databinding.MultipleEmailsSendingQueueLayoutBinding
import com.arhamsoft.online.transferimunityclone.models.fileModel.FileModelRoom

class QueueSendingEmailAdapter(
    private val context: Context,
    private var listFiles: List<FileModelRoom>,
    private val listener: OnCloseImageClickListener
) :
    RecyclerView.Adapter<QueueSendingEmailAdapter.QueueSendingEmailViewHolder>() {

    private lateinit var binding: MultipleEmailsSendingQueueLayoutBinding
    private lateinit var adapter: SendingFilesMonitorAdapter

    class QueueSendingEmailViewHolder(val bind: MultipleEmailsSendingQueueLayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {

        fun onBind(listener: OnCloseImageClickListener, fileModelRoom: FileModelRoom) {
            bind.imgClose.setOnClickListener {
                listener.onCloseImageClickListener(fileModelRoom)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueSendingEmailViewHolder {

        binding = MultipleEmailsSendingQueueLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return QueueSendingEmailViewHolder(binding)

    }

    override fun onBindViewHolder(holder: QueueSendingEmailViewHolder, position: Int) {


        holder.onBind(listener, listFiles[position])
        holder.bind.txtToTxtIn.text = listFiles[position].to
        holder.bind.txtFromTxtIn.text = listFiles[position].from
        holder.bind.txtSubjectTxtIn.text = listFiles[position].subject
        holder.bind.txtDataTxtIn.text = listFiles[position].date
        holder.bind.sendingFileRecycler.layoutManager = LinearLayoutManager(context)
        adapter = SendingFilesMonitorAdapter(listFiles[position])
        holder.bind.sendingFileRecycler.adapter = adapter
        Handler(Looper.getMainLooper()).postDelayed({
            adapter.updateFile(listFiles[position])
        }, 3000)

    }

    override fun getItemCount(): Int {
        return listFiles.size
    }

    fun updateList(list: List<FileModelRoom>) {
        this.listFiles = list
        notifyDataSetChanged()
    }

    interface OnCloseImageClickListener {
        fun onCloseImageClickListener(fileModelRoom: FileModelRoom)
    }
}