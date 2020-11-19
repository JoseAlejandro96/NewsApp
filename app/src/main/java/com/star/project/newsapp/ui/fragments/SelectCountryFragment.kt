package com.star.project.newsapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.star.project.newsapp.R
import com.star.project.newsapp.customviews.CustomMaterialButton
import com.star.project.newsapp.ui.NewsActivity
import com.star.project.newsapp.ui.NewsViewModel
import com.star.project.newsapp.util.Constants
import kotlinx.android.synthetic.main.activity_news.*
import kotlinx.android.synthetic.main.fragment_select_country.*

class SelectCountryFragment : Fragment(R.layout.fragment_select_country) {
    private val countries = listOf("Argentina", "Australia", "Austria", "Belgium", "Brazil",
                                        "Bulgaria", "Canada", "China", "Colombia", "Cuba", "Czech Republic",
                                        "Egypt", "France", "Germany", "Greece", "Hong Kong", "Hungary",
                                        "India", "Indonesia", "Ireland", "Israel", "Italy", "Japan",
                                        "Latvia", "Lithuania", "Malaysia", "Mexico", "Morocco", "Netherlands",
                                        "New Zealand", "Niger", "Norway", "Philippines", "Poland", "Portugal",
                                        "Romania", "Russia", "Saudi Arabia", "Serbia", "Singapore", "Slovakia",
                                        "Slovenia", "South Africa", "South Korea", "Sweden", "Switzerland",
                                        "Taiwan", "Thailand", "Turkey", "UAE", "Ukraine", "United Kingdom",
                                        "United States", "Venezuela")
    private val countyCodes = listOf("ar", "au", "at", "be", "br",
                                        "bg", "ca", "cn", "co", "cu", "cz",
                                        "eg", "fr", "de", "gr", "hk", "hu",
                                        "in", "id", "ie", "il", "it", "jp",
                                        "lv", "it", "my", "mx", "ma", "nl",
                                        "nz", "ng", "no", "ph", "pl", "pt",
                                        "ro", "ru", "sa", "rs", "sg", "sk",
                                        "si", "za", "kr", "se", "ch",
                                        "tw", "th", "tr", "ae", "ua", "gb",
                                        "us", "ve")

    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private var isCountryCodeExists: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initPreferences()
        getMyPreferences()
//        if(isCountryCodeExists){
//            viewModel.currentCountry = preferences.getString(getString(R.string.countryCode), "us")?: "us"
//            findNavController().navigate(R.id.action_selectCountryFragment_to_breakingNewsFragment)
//        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item_select_country, countries)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        setMaterialButton(item_material_button)
        (activity as NewsActivity).bottomNavigationView.visibility = View.INVISIBLE


    }

    @SuppressLint("ResourceType")
    private fun setMaterialButton(view: View){
        val button = CustomMaterialButton(view)
        button.setButtonText("Next")
        button.setButtonTextColor("#FFFFFF")
        button.setBackgroundColor(resources.getString(R.color.purple_500))
        button.setVectorIcon(R.drawable.ic_baseline_done_24)
        button.setProgressVisibility(false)
        button.setProgressColor("#FFFFFF")
        button.setIconColor("#FFFFFF")
        button.setRadiusPixel(5)
        button.getButton().setOnClickListener{
            saveCountryCode()
        }
    }

    private fun saveCountryCode(){
        if (textField.editText?.text.toString().isEmpty()){
            Toast.makeText(requireContext(), "Insert one Country", Toast.LENGTH_SHORT).show()
        }else{

            val countrySelected = textField.editText?.text.toString()
            val indexOfTheCountry = countries.indexOf(countrySelected)
            val code = countyCodes[indexOfTheCountry]
            Toast.makeText(context, code, Toast.LENGTH_SHORT).show()
            editor = preferences.edit().apply {
                putBoolean(getString(R.string.profile_isCodeSaved), true)
                putString(getString(R.string.countryCode), code)
                apply()
            }


            (activity as NewsActivity).bottomNavigationView.visibility = View.VISIBLE
            findNavController().navigate(R.id.action_selectCountryFragment_to_breakingNewsFragment)
        }

    }

    private fun initPreferences(){
        preferences = requireActivity().getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
    private fun getMyPreferences(){
        isCountryCodeExists = preferences.getBoolean(getString(R.string.profile_isCodeSaved), false)
    }
}