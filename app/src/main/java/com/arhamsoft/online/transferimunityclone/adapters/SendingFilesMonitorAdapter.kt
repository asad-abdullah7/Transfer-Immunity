package com.arhamsoft.online.transferimunityclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.online.transferimunityclone.R
import com.arhamsoft.online.transferimunityclone.databinding.SendingSingleItemLayoutBinding
import com.arhamsoft.online.transferimunityclone.models.fileModel.FileModelRoom

class SendingFilesMonitorAdapter(private var file: FileModelRoom) :
    RecyclerView.Adapter<SendingFilesMonitorAdapter.SendingFileViewHolder>() {

    private lateinit var binding: SendingSingleItemLayoutBinding

    class SendingFileViewHolder(val bind: SendingSingleItemLayoutBinding) : RecyclerView.ViewHolder(bind.root) {


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SendingFileViewHolder {

        binding = SendingSingleItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SendingFileViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SendingFileViewHolder, position: Int) {
        holder.bind.imageAttachment.setImageURI(file.filePath!![position].toUri())
        holder.bind.txtFileName.text = file.fileName!![position]
        holder.bind.txtSize.text = file.fileSize!![position]
        holder.bind.progressBarStatus.max = 100
        holder.bind.progressBarStatus.progress = file.progress!![position].toInt()
        if (file.status!![position].toInt() == 1){
            holder.bind.imgStatusUploading.setImageResource(R.drawable.done)
        }
    }

    override fun getItemCount(): Int {
        return file.fileName!!.size
    }

    fun updateFile(fileRoom: FileModelRoom){
        this.file = fileRoom
    }
}