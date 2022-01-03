package com.arhamsoft.online.transferimunityclone.models.fileModel

import com.google.gson.annotations.SerializedName

data class UploadedFileModelResponse(
    val data: String,

    @SerializedName("file_id")
    val fileID: Int,

    @SerializedName("remaining_allocated_space")
    val remainingAllocatedSpace: Double,

    val code: Long,
    val status: Long,

    @SerializedName("virus_infected")
    val virusInfected: Long,

    @SerializedName("single_file_response")
    val singleFileResponse: Long,

    val message: String
) {

}