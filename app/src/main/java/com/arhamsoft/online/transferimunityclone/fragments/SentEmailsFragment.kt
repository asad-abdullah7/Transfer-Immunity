package com.arhamsoft.online.transferimunityclone.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arhamsoft.online.transferimunityclone.adapters.SentEmailAdapter
import com.arhamsoft.online.transferimunityclone.databinding.FragmentSentEmailsBinding
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.ApiCall
import com.arhamsoft.online.transferimunityclone.handlers.interfaces.URLs
import com.arhamsoft.online.transferimunityclone.models.emailModel.EmailDataModel
import com.arhamsoft.online.transferimunityclone.models.emailModel.EmailModel
import com.arhamsoft.online.transferimunityclone.utils.functionsCommon.CommonFunctions
import com.arhamsoft.online.transferimunityclone.utils.sharedPreferences.CustomSharedPref
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SentEmailsFragment : Fragment() {

    private lateinit var binding: FragmentSentEmailsBinding
    private var emailList: List<EmailDataModel> = ArrayList()
    private lateinit var sharedPref: CustomSharedPref
    private var userToken: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSentEmailsBinding.inflate(layoutInflater, container, false)
        sharedPref = CustomSharedPref(requireContext())

        binding.pullToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                setViews()
                binding.pullToRefresh.isRefreshing = false
            }

        })
        setViews()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
    }

    private fun setViews() {

        userToken = sharedPref.getValueString("TOKEN")
        Log.e("tag", "setViews: $userToken")
        getEmailsFromApiResponse()

    }

    private fun getEmailsFromApiResponse() {
        retrofitCalling()
    }

    private fun retrofitCalling() {


        val thread = Thread {

            val client = OkHttpClient.Builder().addInterceptor { chain ->
                val newRequest: Request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $userToken")
                    .build()
                chain.proceed(newRequest)
            }.build()


            val retrofitBuilder = Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLs.baseURL)
                .build()
                .create(ApiCall::class.java)

            val retrofitData = retrofitBuilder.fetchEmails()

            retrofitData.enqueue(
                object : Callback<EmailModel> {
                    override fun onResponse(
                        call: Call<EmailModel>,
                        response: Response<EmailModel>,
                    ) {

                        binding.loadingDashBoard.loadingBar.visibility = View.GONE

                        val emailModel = response.body()
                        Log.e("tag", "onResponse: ${emailModel.toString()}")
                        if (emailModel?.status?.toInt() == 1) {
                            emailList = emailModel.data
                            Log.e("tag", "onResponse: ${emailList.toString()}")
                            binding.recyclerViewSentEmails.layoutManager =
                                LinearLayoutManager(requireContext())
                            binding.recyclerViewSentEmails.adapter =
                                SentEmailAdapter(requireContext(), emailList)
                        } else {
                            CommonFunctions.showDialog(
                                requireActivity(),
                                "Ops..!!",
                                emailModel?.message!!,
                                1
                            )
                        }

                    }

                    override fun onFailure(call: Call<EmailModel>, t: Throwable) {

                        binding.loadingDashBoard.loadingBar.visibility = View.GONE
                        CommonFunctions.showDialog(
                            requireActivity(),
                            "Ops..!!",
                            t.message!!,
                            1
                        )
                        Log.e("Error: ", t.toString())
                    }

                },
            )

        }

        thread.start()
        thread.join()

    }

}