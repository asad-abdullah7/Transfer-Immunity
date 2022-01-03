package com.arhamsoft.online.transferimunityclone.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.arhamsoft.online.transferimunityclone.adapters.CountryAdapter
import com.arhamsoft.online.transferimunityclone.databinding.ActivityCountryBinding
import com.arhamsoft.online.transferimunityclone.models.countryModel.CountrySingle
import com.arhamsoft.online.transferimunityclone.utils.staticFields.StaticFields

class CountryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryBinding
    private lateinit var countryList: ArrayList<CountrySingle>
    private lateinit var searchCountryList: ArrayList<CountrySingle>
    private lateinit var adapter: CountryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        countryList = ArrayList()
        countryList = getValuesFromIntent()
        setListeners()
        setRecyclerValues(countryList)
    }

    private fun setListeners() {

        binding.crossImage.setOnClickListener {
            onBackPressed()
        }
        searchCountryList = ArrayList()
        binding.editTxtEmailLogin.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.e("tag", "beforeTextChanged: $s")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchCountryList = ArrayList()
                Log.e("tag", "onTextChanged: $s")
                for (i in 0 until countryList.size) {
                    if (countryList[i].name.contains(s!!, true)) {
                        val countrySingle = countryList[i]
                        searchCountryList.add(countrySingle)
                    }
                }
                adapter.updateDataIntoCountryAdapter(searchCountryList)
                adapter.notifyDataSetChanged()

            }

            override fun afterTextChanged(s: Editable?) {
                Log.e("tag", "afterTextChanged: $s")
            }

        })

    }

    private fun getValuesFromIntent(): ArrayList<CountrySingle> {
        return intent.getSerializableExtra("list") as ArrayList<CountrySingle>
    }

    private fun setRecyclerValues(list: ArrayList<CountrySingle>) {

        binding.recyclerCountry.layoutManager = LinearLayoutManager(this)
        adapter = CountryAdapter(list, object : CountryAdapter.OnCountrySelectListener {
            override fun onCountrySelect(country: CountrySingle) {
                Log.e("tag", "onCountrySelect: ${country.name}")

                StaticFields.countryId = country.id.toInt()
                StaticFields.countryName = country.name
                onBackPressed()
            }

        })
        binding.recyclerCountry.adapter = adapter

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}