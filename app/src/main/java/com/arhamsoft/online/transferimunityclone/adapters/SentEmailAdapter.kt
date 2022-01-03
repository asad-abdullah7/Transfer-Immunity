package com.arhamsoft.online.transferimunityclone.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arhamsoft.online.transferimunityclone.databinding.SingleSentEmailItemBinding
import com.arhamsoft.online.transferimunityclone.models.emailModel.EmailDataModel
import com.squareup.picasso.Picasso

class SentEmailAdapter(private val context: Context,private val list: List<EmailDataModel>) : RecyclerView.Adapter<SentEmailAdapter.SentEmailViewHolder>() {

    private lateinit var binding: SingleSentEmailItemBinding

    class SentEmailViewHolder(val bind: SingleSentEmailItemBinding) : RecyclerView.ViewHolder(bind.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SentEmailViewHolder {

        binding = SingleSentEmailItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
         return SentEmailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SentEmailViewHolder, position: Int) {

        holder.bind.userMailDate.text = list[position].date
        holder.bind.userMailEmail.text = list[position].to
        holder.bind.recyclerViewSingleEmailAttachment.layoutManager = LinearLayoutManager(context)
        holder.bind.recyclerViewSingleEmailAttachment.adapter = SentEmailAttachmentAdapter(list[position].files)




    }

    override fun getItemCount(): Int {
       return list.size
    }
}