package com.arhamsoft.online.transferimunityclone.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.arhamsoft.online.transferimunityclone.activities.UploadingFilesListActivity
import com.arhamsoft.online.transferimunityclone.adapters.AttachedFilesAdapter
import com.arhamsoft.online.transferimunityclone.databinding.FragmentNewEmailBinding
import com.arhamsoft.online.transferimunityclone.models.fileModel.FileModelRoom
import com.arhamsoft.online.transferimunityclone.roomDataBase.AppDataBase
import com.arhamsoft.online.transferimunityclone.utils.functionsCommon.CommonFunctions
import com.arhamsoft.online.transferimunityclone.utils.sharedPreferences.CustomSharedPref
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields
import com.arhamsoft.online.transferimunityclone.workManager.AttachmentUploadAndSendEmailWorkManager


class NewEmailFragment : Fragment() {

    private lateinit var binding: FragmentNewEmailBinding
    private val READ_STORAGE_PERMISSION_REQUEST_CODE = 41
    private lateinit var adapter: AttachedFilesAdapter

    //    private var counter = 1
    private lateinit var fileModelRoom: FileModelRoom
    private lateinit var sharedPref: CustomSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewEmailBinding.inflate(layoutInflater, container, false)

        setViews()
        return binding.root
    }

    // Set Data Into Views
    private fun setViews() {

        binding.editTxtEmailFrom.setText(StaticFields.loginData.email)
        sharedPref = CustomSharedPref(requireContext())

        setRecyclerView()

        //set Listeners
        setListeners()
    }

    // Set Values into Recycler view
    private fun setRecyclerView() {
        binding.attechedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = AttachedFilesAdapter(requireContext(), object : AttachedFilesAdapter.OnItemClick {
            override fun onItemClick(position: Int) {
                StaticFields.docList.removeAt(position)
                adapter.notifyDataSetChanged()
                if (StaticFields.docList.size == 0) {
                    binding.cancelFabForAttachments.visibility = View.GONE
                }
            }

        })
        binding.attechedRecyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        if (StaticFields.docList.size > 0) {
            binding.cancelFabForAttachments.visibility = View.VISIBLE
        }
        adapter.updateList()
    }

    // OnClickListeners
    private fun setListeners() {

        //Bottom Sheet
        binding.fabForAttachments.setOnClickListener {

            if (checkPermissionForReadExternalStorage()) {
                setBottomSheetFragment()
            } else {
                requestPermissionForReadExternalStorage()
            }
        }


        binding.uploadingActivity.setOnClickListener {
            startActivity(Intent(requireContext(), UploadingFilesListActivity::class.java))
        }

        //cancel All Attachments
        binding.cancelFabForAttachments.setOnClickListener {
            StaticFields.docList.removeAll(StaticFields.docList.toSet())
            adapter.notifyDataSetChanged()
            if (StaticFields.docList.size == 0)
                binding.cancelFabForAttachments.visibility = View.GONE
        }

        //Send Email
        binding.sendEmailBtn.setOnClickListener {
            //Send here
            gatherDataBeforeSavingIntoDB()

        }


    }

    // make file object with data
    private fun gatherDataBeforeSavingIntoDB() {

        if (binding.editTxtEmailTo.text!!.isEmpty()) {
            binding.editTxtEmailTo.error = "fill the field!"
        } else {
            if (binding.editTxtEmailSubject.text!!.isEmpty()) {
                binding.editTxtEmailSubject.error = "fill the field!"
            } else {
                if (binding.editTxtEmailMessage.text!!.isEmpty()) {
                    binding.editTxtEmailMessage.error = "fill the field!"
                } else {

                    binding.loadingDashBoard.loadingBar.visibility = View.VISIBLE

                    val nameList = ArrayList<String>()
                    val pathList = ArrayList<String>()
                    val sizeList = ArrayList<String>()
                    val progressList = ArrayList<String>()
                    val statusList = ArrayList<String>()
                    val linksList = ArrayList<String>()
                    val idList = ArrayList<String>()

                    for (i in 0 until StaticFields.docList.size) {
                        nameList.add(StaticFields.docList[i].fileName)
                        pathList.add(StaticFields.docList[i].filePath)
                        sizeList.add(StaticFields.docList[i].fileSize)
                        statusList.add("0")
                        progressList.add("0")
                        linksList.add("")
                        idList.add("")

                    }

                    fileModelRoom = FileModelRoom(
                        binding.editTxtEmailTo.text.toString(),
                        binding.editTxtEmailFrom.text.toString(),
                        binding.editTxtEmailSubject.text.toString(),
                        binding.editTxtEmailMessage.text.toString(),
                        "",
                        nameList,
                        idList,
                        pathList,
                        linksList,
                        statusList,
                        progressList,
                        sizeList
                    )


                    saveAttachmentsAndEmailDataIntoDataBase(fileModelRoom)
                    binding.loadingDashBoard.loadingBar.visibility = View.GONE

                }
            }
        }

    }


    // Bottom Sheet ( For Permissions )
    private fun setBottomSheetFragment() {
        val bottomSheetDialog = BottomSheetFragment.newInstance()
        bottomSheetDialog.show(
            requireActivity().supportFragmentManager,
            "Bottom Sheet Dialog Fragment"
        )
        bottomSheetDialog.isCancelable = false
    }

    // Check Permissions
    private fun checkPermissionForReadExternalStorage(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result =
                requireContext().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    // Request Permissions
    @Throws(Exception::class)
    private fun requestPermissionForReadExternalStorage() {
        try {
            ActivityCompat.requestPermissions(
                (context as Activity?)!!, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                READ_STORAGE_PERMISSION_REQUEST_CODE
            )
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    //Clear Records
    private fun clearRecords() {

        StaticFields.docList.removeAll(StaticFields.docList.toSet())
        binding.editTxtEmailMessage.setText("")
        binding.editTxtEmailTo.setText("")
        binding.editTxtEmailSubject.setText("")
        binding.cancelFabForAttachments.visibility = View.GONE
    }

    // Save Into DB
    private fun saveAttachmentsAndEmailDataIntoDataBase(fileModelRoom: FileModelRoom) {

        val thread = Thread {
            AppDataBase.getAppDB(requireContext())?.fileDAO()?.insertFile(fileModelRoom)
        }
        thread.start()
        thread.join()

        if (!sharedPref.isUploading("UP")) {

            startWorkManager()
        }

        clearRecords()
        CommonFunctions.showDialog(
            requireActivity(),
            "Sending...",
            "Your email is sending in background Thank you.",
            2
        )

    }

    // Start WorkManager Service
    private fun startWorkManager() {

        val builder =
            OneTimeWorkRequest.Builder(AttachmentUploadAndSendEmailWorkManager::class.java)

        val request = builder.build()
        WorkManager.getInstance(
            requireContext()
        ).enqueue(request)

    }

}