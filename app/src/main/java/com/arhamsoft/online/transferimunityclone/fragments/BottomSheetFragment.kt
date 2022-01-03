package com.arhamsoft.online.transferimunityclone.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.arhamsoft.online.transferimunityclone.databinding.BottomSheetDialogBinding
import com.arhamsoft.online.transferimunityclone.models.attachmentModel.FileAttachmentModel
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File


class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetDialogBinding
    private lateinit var getImageIntent:
            ActivityResultLauncher<Intent>

    companion object {
        fun newInstance(): BottomSheetFragment {
            return BottomSheetFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun setupDialog(dialog: Dialog, style: Int) {
        binding = BottomSheetDialogBinding.inflate(LayoutInflater.from(requireContext()))
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)

        setListeners()

        getImageIntent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val data = result?.data
                if (data != null) {
                    getFileObject(data.data!!)
                }
            }

    }


    private fun setListeners() {

        binding.txtOpenGallery.setOnClickListener {
            //Open Gallery
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            getImageIntent.launch(gallery)
        }

        binding.txtOpenFiles.setOnClickListener {
            //Open File
            val filePicker = Intent()
            filePicker.type = "*/*"
            filePicker.action = Intent.ACTION_GET_CONTENT
            getImageIntent.launch(filePicker)
        }
        binding.txtCancel.setOnClickListener {
            dialog?.dismiss()
        }


    }

    @SuppressLint("Range")
    private fun getFileObject(docPath: Uri) {

        val returnCursor: Cursor? =
            requireContext().contentResolver.query(docPath, null, null, null, null)

        val fileAttachmentModel = FileAttachmentModel()
        if (returnCursor != null) {
            returnCursor.moveToFirst()
            fileAttachmentModel.filePath =
                returnCursor.getString(returnCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
            val file =
                File(returnCursor.getString(returnCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)))
            fileAttachmentModel.fileName = file.name
            val longSize =
                returnCursor.getLong(returnCursor.getColumnIndex(MediaStore.Images.Media.SIZE))
            val sizeInKbs: Double = (longSize / 1024).toDouble()
            val sizeInMbs: Double = sizeInKbs / 1024
            val number3digits: Double = String.format("%.3f", sizeInMbs).toDouble()
            val number2digits: Double = String.format("%.2f", number3digits).toDouble()
            fileAttachmentModel.fileSize = "$number2digits MB"
        }

        StaticFields.docList.add(fileAttachmentModel)
        dialog?.dismiss()

    }
}