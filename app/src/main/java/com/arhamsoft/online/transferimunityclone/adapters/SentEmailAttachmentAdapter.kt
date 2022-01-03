package com.arhamsoft.online.transferimunityclone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.online.transferimunityclone.databinding.SingleAttachmentLayoutBinding
import com.arhamsoft.online.transferimunityclone.models.emailModel.AttachedFileModel

class SentEmailAttachmentAdapter(private val list: List<AttachedFileModel>) :
    RecyclerView.Adapter<SentEmailAttachmentAdapter.SentEmailAttachedViewHolder>() {

    private lateinit var binding: SingleAttachmentLayoutBinding

    class SentEmailAttachedViewHolder(val bind: SingleAttachmentLayoutBinding) :
        RecyclerView.ViewHolder(bind.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentEmailAttachedViewHolder {

        binding = SingleAttachmentLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SentEmailAttachedViewHolder(binding)

    }

    override fun onBindViewHolder(holder: SentEmailAttachedViewHolder, position: Int) {
        list[position].name.also { holder.bind.txtFileName.text = it }
        holder.bind.txtFileSize.text = list[position].size
    }

    override fun getItemCount(): Int {
        return list.size
    }
}