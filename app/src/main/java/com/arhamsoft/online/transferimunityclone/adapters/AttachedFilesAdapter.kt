package com.arhamsoft.online.transferimunityclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.online.transferimunityclone.databinding.AttachedFilesLayoutBinding
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields

class AttachedFilesAdapter(private val context: Context, val listener: OnItemClick) :
    RecyclerView.Adapter<AttachedFilesAdapter.AttachedViewModel>() {
    private var attachmentList = StaticFields.docList
    private lateinit var binding: AttachedFilesLayoutBinding

    class AttachedViewModel(val bind: AttachedFilesLayoutBinding) : RecyclerView.ViewHolder(bind.root) {
            fun onBind(position: Int, onItemClick: OnItemClick) {
                bind.closeImage.setOnClickListener {
                    onItemClick.onItemClick(position)
                }
            }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AttachedViewModel {
        binding =
            AttachedFilesLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AttachedViewModel(binding)
    }

    override fun onBindViewHolder(holder: AttachedViewModel, position: Int) {
        holder.bind.imageAttachment.setImageURI(attachmentList[position].filePath.toUri())
        holder.bind.txtFileName.text = attachmentList[position].fileName
        holder.bind.txtFileSize.text = attachmentList[position].fileSize.toString()
        holder.onBind(position,listener)
    }

    override fun getItemCount(): Int {
        return attachmentList.size
    }

    fun updateList() {
        this.attachmentList = StaticFields.docList
        notifyDataSetChanged()
    }

    interface OnItemClick{
        fun onItemClick(position: Int)
    }
}