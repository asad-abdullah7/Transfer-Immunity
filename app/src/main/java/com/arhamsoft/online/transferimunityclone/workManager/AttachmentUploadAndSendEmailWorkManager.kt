package com.arhamsoft.online.transferimunityclone.workManager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.androidnetworking.interfaces.UploadProgressListener
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.ApiCall
import com.arhamsoft.online.transferimunityclone.models.fileModel.FileModelRoom
import com.arhamsoft.online.transferimunityclone.models.fileModel.UploadedFileModelResponse
import com.arhamsoft.online.transferimunityclone.models.emailModel.SentEmailResponseBack
import com.arhamsoft.online.transferimunityclone.roomDataBase.AppDataBase
import com.arhamsoft.online.transferimunityclone.utils.sharedPreferences.CustomSharedPref
import com.arhamsoft.online.transferimunityclone.utils.notifications.NotificationUtils
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.URLs
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class AttachmentUploadAndSendEmailWorkManager(val context: Context, params: WorkerParameters) :
    Worker(context, params) {

    val sharedPref = CustomSharedPref(context)
    private var counter = 1
    override fun doWork(): Result {


        val fileModelRoom = AppDataBase.getAppDB(context)?.fileDAO()?.getFiles()
        if (fileModelRoom != null) {

            if (!sharedPref.isUploading("UP")) {
                sharedPref.saveUploading("UP", true)
                if (fileModelRoom.fileName!!.size > 0) {

                    var uploaderCheck = false
                    // add all files
                    for (i in 0 until fileModelRoom.fileName!!.size) {

                        if (fileModelRoom.fileLink!![i] == "") {
                            uploaderCheck = true
                            uploadFilesAndSendEmail(fileModelRoom, i)
                        } else {
                            uploaderCheck = false
                            //AppDataBase.getAppDB(context)?.fileDAO()?.deleteFile(fileModelRoom)
                        }
                    }

                    if (!fileModelRoom.fileLink!!.contains("")) {
                        AppDataBase.getAppDB(context)?.fileDAO()?.deleteFile(fileModelRoom)
                    }

                } else {
                    retroFitCalling(fileModelRoom, "")
                }
            }

        } else {
            Log.e("work", "doWork: No Data in Queue")
//            AndroidNetworking.cancel("file")
        }

        return Result.success()
    }

    // upload Attachments To Server
    private fun uploadFilesAndSendEmail(fileRoom: FileModelRoom, index: Int) {

        val fileIdsList = ArrayList<String>()
        counter = 1

        val file = File(fileRoom.filePath!![index])
        AndroidNetworking.upload(URLs.baseURL + URLs.uploadFile)
            .addHeaders("Authorization", StaticFields.loginData.accessToken)
            .addMultipartFile("file", file)
            .setTag("file")
            .addMultipartParameter("hash_id", StaticFields.loginData.hashID)
            .addMultipartParameter("platform", "mobile")
            .addMultipartParameter("lang", StaticFields.loginData.language)
            .setPriority(Priority.HIGH)
            .build()
            .setUploadProgressListener(object : UploadProgressListener {
                override fun onProgress(bytesUploaded: Long, totalBytes: Long) {
                    Log.e("upload", "onProgress: $bytesUploaded")

                    val percentage =
                        ((bytesUploaded.toDouble() / totalBytes.toDouble()) * 100).toInt()
                    fileRoom.progress!![index] = percentage.toString()

                    val thread = Thread {

                        AppDataBase.getAppDB(context)?.fileDAO()?.updateFile(fileRoom)

                        Log.e("upload prog", "onProgress: $percentage")
                        Log.e("upload", "onProgress: ${fileRoom.progress!![0]}")
                    }
                    thread.start()
                    thread.join()
                }
            })
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    Log.e("upload", "onResponse: ${response.toString()}")
                    val uploadedFileModelResponse = getJsonResponse(response.toString())

                    Log.e("upload", "onResponse: ${uploadedFileModelResponse.status}")

                    val thread = Thread {
                        fileRoom.fileIds!![index] = uploadedFileModelResponse.fileID.toString()
//                                fileLinksList.add(uploadedFileModelResponse.data)
                        fileRoom.status!![index] = uploadedFileModelResponse.status.toString()
                        fileRoom.fileLink!![index] = uploadedFileModelResponse.data
                        AppDataBase.getAppDB(context)?.fileDAO()?.updateFile(fileRoom)
                    }
                    thread.start()
                    thread.join()
                    var flag = false
                    for (item in fileRoom.fileLink!!) {
                        if (item.isNotEmpty()) {
                            flag = true
                        } else {
                            flag = false
                            break
                        }
                    }
                    if (flag) {
                        val thread = Thread {

//                                    fileRoom.fileLink = fileLinksList
                            AppDataBase.getAppDB(context)?.fileDAO()?.updateFile(fileRoom)
                            sharedPref.saveUploading("UP", false)
                            getIds(fileRoom)
                        }
                        thread.start()
                        thread.join()
                    } else {
                        val thread = Thread {

                            AppDataBase.getAppDB(context)?.fileDAO()?.updateFile(fileRoom)
                        }
                        thread.start()
                        thread.join()
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.e("upload", "onResponse: ${anError.toString()}")
                }

            })


        /*  else {
             AppDataBase.getAppDB(context)?.fileDAO()?.deleteFile(fileRoom)
         }*/


    }

    // Get IDs For Sending Email to Server
    private fun getIds(modelRoom: FileModelRoom) {

        var filesIds = ""
        modelRoom.message += "<br><br> Please check the following link(s) to get file(s):"
        for (links in 0 until modelRoom.fileLink!!.size) {
            filesIds += "${modelRoom.fileIds!![links]},"
            modelRoom.message += "<br>" + " <a href=\"${modelRoom.fileLink!![links]}\">${modelRoom.fileName!![links]}</a>"
        }
        if (!filesIds.isNullOrEmpty()) {
            filesIds.removeSuffix(",")
            filesIds.substring(0, filesIds.length - 1)
        }


        sendMail(modelRoom, filesIds)
    }

    //JSON to Object Conversion
    private fun getJsonResponse(json: String): UploadedFileModelResponse {
        return Gson().fromJson(json, UploadedFileModelResponse::class.java)
    }

    //Send Email
    private fun sendMail(modelRoom: FileModelRoom, filesIds: String) {

        retroFitCalling(modelRoom, filesIds)

    }

    //Call Send Email API With Attachments
    private fun retroFitCalling(room: FileModelRoom, filesIds: String) {

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${StaticFields.loginData.accessToken}")
                .build()
            chain.proceed(newRequest)
        }.build()


        val retrofitBuilder = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URLs.baseURL)
            .build()
            .create(ApiCall::class.java)

        val retrofitData = retrofitBuilder.sendEmailStore(
            if (room.to.isNullOrEmpty()) "haalim376@gmail.com" else room.to!!,
            room.from.toString(),
            room.subject ?: "!23",
            room.message ?: " ",
            filesIds,
            "mobile",
            StaticFields.loginData.language
        )

        retrofitData.enqueue(object : Callback<SentEmailResponseBack> {
            override fun onResponse(
                call: Call<SentEmailResponseBack>,
                response: Response<SentEmailResponseBack>
            ) {
                if (response.code() == 200) {
                    Log.e("emailSent", "onResponse: Uploaded")
                    Log.e("emailSent", "onResponse: $response")
                    //Delete Email From DataBase
                    val thread = Thread {
                        NotificationUtils.createNotification(context, room.to!!, room.subject!!)
                        AppDataBase.getAppDB(context)?.fileDAO()?.deleteFile(room)
                        doWork()
                    }
                    thread.start()
                    thread.join()

                } else {
                    Log.e("emailSent", "onResponse: Error")
                    Log.e("emailSent", "onResponse: ${response.message()}")
                    doWork()
                }
            }

            override fun onFailure(call: Call<SentEmailResponseBack>, t: Throwable) {
                Log.e("emailSent", "onFailure: ${t.printStackTrace()}")
                val thread = Thread {
//                    Toast.makeText(context, "${t.message}  | Retrying", Toast.LENGTH_LONG).show()
//                    AppDataBase.getAppDB(context)?.fileDAO()?.deleteFile(room)
                    doWork()
                }
                thread.start()
                thread.join()
            }

        })

    }


}