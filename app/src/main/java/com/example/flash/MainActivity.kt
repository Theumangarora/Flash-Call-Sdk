package com.example.flash

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.flashcall_sdk.FlashCallback
import com.example.flashcall_sdk.FlashSDK
import com.google.android.material.bottomsheet.BottomSheetDialog


class MainActivity : AppCompatActivity(), FlashCallback {
    private lateinit var countryPickerLayout: LinearLayout
    private lateinit var countryFlagText: TextView
    private lateinit var countryNameText: TextView
    private lateinit var countryCodeText: TextView
    private lateinit var selectedCountryCode: TextView
    private lateinit var phoneNumberInput: EditText
    private lateinit var nextButton: Button
    private lateinit var progressBarContainer: FrameLayout

    private var selectedCountry: Country? = null
    private lateinit var countryAdapter: CountryAdapter
    private lateinit var flashSDK: FlashSDK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        flashSDK = FlashSDK.getInstance()

        initializeViews()
        setupClickListeners()
        setupPhoneNumberInput()
        }

    override fun onSuccess(s: String) {
        Log.d("hii>>", "onFailure: "+s)

        progressBarContainer.visibility = View.GONE;

        val dialog = Dialog(this)
        dialog.setContentView(
             R.layout.customview_layout
        )

        val message: TextView = dialog.findViewById(
            R.id.tv_msg
        )
        val btnPrimary: Button = dialog.findViewById(
            R.id.btnContinue
        )
        dialog.setCancelable(false)


        btnPrimary.setOnClickListener {
            dialog.dismiss()
            // Add primary button action
        }

        dialog.show()
    }

    override fun onFailure(s: String) {
        Log.d("hii>>", "onFailure: "+s)
        progressBarContainer.visibility = View.GONE;

        val dialog = Dialog(this)
        dialog.setContentView(
            R.layout.failure
        )

        val btnPrimary: Button = dialog.findViewById(
            R.id.btnTryAgain
        )
        val message: TextView = dialog.findViewById(
            R.id.tv_msg
        )


        dialog.setCancelable(false)

        btnPrimary.setOnClickListener {
            dialog.dismiss()
            // Add primary button action
        }

        dialog.show()

    }


    private fun initializeViews() {
        countryPickerLayout = findViewById(R.id.countryPickerLayout)
        countryFlagText = findViewById(R.id.countryFlagText)
        countryNameText = findViewById(R.id.countryNameText)
        countryCodeText = findViewById(R.id.countryCodeText)
        selectedCountryCode = findViewById(R.id.selectedCountryCode)
        phoneNumberInput = findViewById(R.id.phoneNumberInput)
        nextButton = findViewById(R.id.nextButton)
        progressBarContainer= findViewById(R.id.progressBarContainer)

        flashSDK.initialize(
            context = this,
            baseUrl = "https://omniapi.routevoice.com/" ,
            token = "b332c03966e398929cf478b8307cca10b5ebd0bb33c5ee045f0f1fed7f3f33ba",
            appId = "14251D3751",
            this
                  )

    }

    private fun setupClickListeners() {
        countryPickerLayout.setOnClickListener {
            showCountryPickerDialog()
        }

        nextButton.setOnClickListener {
            progressBarContainer.visibility = View.VISIBLE;


            val phoneNumber = "${selectedCountry?.code?.replace("+", "")}${phoneNumberInput.text}"
//            Toast.makeText(this, "Verifying: $phoneNumber", Toast.LENGTH_SHORT).show()

                flashSDK.startVerification(phoneNumber,selectedCountry!!.name)

        }
    }
         fun setupPhoneNumberInput() {
            phoneNumberInput.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    updateNextButtonState()
                }
            })
        }

    private fun showCountryPickerDialog() {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_country_picker, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.countriesRecyclerView)
        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)

        // Initialize RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        countryAdapter = CountryAdapter(countries) { country ->
            selectedCountry = country
            updateCountrySelection()
            dialog.dismiss()
        }
        recyclerView.adapter = countryAdapter

        // Setup search functionality
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                Log.d("ddd>>", "onTextChanged: "+s.toString())
                countryAdapter.filter(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        dialog.setContentView(view)
        dialog.show()
    }

        private fun updateCountrySelection() {
            selectedCountry?.let { country ->
                countryFlagText.text = country.flag
                countryNameText.text = country.name
                countryCodeText.text = country.code
                selectedCountryCode.text = country.code
                updateNextButtonState()
            }
        }

        private fun updateNextButtonState() {
            val isValid = selectedCountry != null &&
                    phoneNumberInput.text.isNotEmpty() &&
                    phoneNumberInput.text.length >= 6
            nextButton.isEnabled = isValid
        }
    }






