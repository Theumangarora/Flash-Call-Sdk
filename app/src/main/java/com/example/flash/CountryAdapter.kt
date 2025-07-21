package com.example.flash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(
    private val allCountries: List<Country>,
    private val onCountrySelected: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private var filteredCountries: List<Country> = allCountries

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val flagText: TextView = view.findViewById(R.id.countryFlagText)
        val nameText: TextView = view.findViewById(R.id.countryNameText)
        val codeText: TextView = view.findViewById(R.id.countryCodeText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_country, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val country = filteredCountries[position]
        holder.flagText.text = country.flag
        holder.nameText.text = country.name
        holder.codeText.text = country.code

        holder.itemView.setOnClickListener {
            onCountrySelected(country)
        }
    }

    override fun getItemCount() = filteredCountries.size

    fun filter(query: String) {
        filteredCountries = if (query.isEmpty()) {
            allCountries
        } else {
            allCountries.filter { country ->
                country.name.lowercase().contains(query.lowercase()) ||
                        country.code.contains(query)
            }
        }
        notifyDataSetChanged()
    }
}