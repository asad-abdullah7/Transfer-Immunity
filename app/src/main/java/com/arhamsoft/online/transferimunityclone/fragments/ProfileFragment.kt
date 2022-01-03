package com.arhamsoft.online.transferimunityclone.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.arhamsoft.online.transferimunityclone.activities.DashboardActivity
import com.arhamsoft.online.transferimunityclone.activities.LoginActivity
import com.arhamsoft.online.transferimunityclone.databinding.FragmentProfileBinding
import com.arhamsoft.online.transferimunityclone.roomDataBase.AppDataBase
import com.arhamsoft.online.transferimunityclone.utils.sharedPreferences.CustomSharedPref
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPref: CustomSharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        sharedPref = CustomSharedPref(
            requireContext()
        )
        setViews()
        setOnClickListeners()

        return binding.root
    }

    private fun setOnClickListeners() {

        binding.dashBoardProfile.setOnClickListener {
            val i = Intent(requireActivity(), DashboardActivity::class.java)
            i.putExtra("user_id", StaticFields.loginData.id)
            i.putExtra("lang", StaticFields.loginData.language)
            i.putExtra("check", 0)
            startActivity(i)
        }


        binding.packageDetailsProfile.setOnClickListener {
            val i = Intent(requireActivity(), DashboardActivity::class.java)
            i.putExtra("user_id", StaticFields.loginData.id)
            i.putExtra("lang", StaticFields.loginData.language)
            i.putExtra("check", 1)
            startActivity(i)
        }

        binding.signOutProfile.setOnClickListener {
            val thread = Thread {
                AppDataBase.getAppDB(requireContext())?.userDAO()
                    ?.deleteUser(StaticFields.loginData)
                sharedPref.clearSharedPreference()

                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                activity?.startActivity(intent)

            }
            thread.start()
            thread.join()
        }

    }

    private fun setViews() {
        binding.languageProfile.text = StaticFields.loginData.language
        Picasso.get().load(StaticFields.loginData.profileImagePath).into(binding.profileImage)
        binding.userEmailProfile.text = StaticFields.loginData.email
        binding.txtUserName.text = StaticFields.loginData.name
    }
}