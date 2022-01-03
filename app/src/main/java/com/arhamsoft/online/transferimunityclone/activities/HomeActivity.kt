package com.arhamsoft.online.transferimunityclone.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.arhamsoft.online.transferimunityclone.R
import com.arhamsoft.online.transferimunityclone.adapters.ViewPagerAdapter
import com.arhamsoft.online.transferimunityclone.databinding.ActivityHomeBinding
import com.arhamsoft.online.transferimunityclone.fragments.NewEmailFragment
import com.arhamsoft.online.transferimunityclone.fragments.ProfileFragment
import com.arhamsoft.online.transferimunityclone.fragments.SentEmailsFragment
import com.arhamsoft.online.transferimunityclone.workManager.AttachmentUploadAndSendEmailWorkManager
import com.google.android.material.navigation.NavigationBarView

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setViews()
        //startWorkManager()

    }

    override fun onResume() {
        super.onResume()

    }

    // Start WorkManager Service
    private fun startWorkManager() {

        val builder =
            OneTimeWorkRequest.Builder(AttachmentUploadAndSendEmailWorkManager::class.java)

        val request = builder.build()
        WorkManager.getInstance(
            this
        ).enqueue(request)

    }

    private fun setViews() {

        setPagesIntoViewPager()
        setBottomNavigationView()
    }

    private fun setPagesIntoViewPager() {
        val adapter = ViewPagerAdapter(this)
        adapter.addFragments(NewEmailFragment())
        adapter.addFragments(SentEmailsFragment())
        adapter.addFragments(ProfileFragment())
        binding.pager.adapter = adapter
    }

    private fun setBottomNavigationView() {

        binding.bottomNavigation.setOnItemSelectedListener(object :
            NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when (item.itemId) {
                    R.id.newEmail -> binding.pager.currentItem = 0
                    R.id.sentEmails -> binding.pager.currentItem = 1
                    R.id.userProfile -> binding.pager.currentItem = 2
                }
                return true
            }

        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }
}