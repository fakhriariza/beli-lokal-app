package com.example.belilokalapp

import android.content.Context
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class PrefManager(var context: Context?) {
    fun getRupiahFormat(price: Int?): String? {
        if (price != null) {
            val kursIndonesia = DecimalFormat.getCurrencyInstance() as DecimalFormat
            val formatRp = DecimalFormatSymbols()
            formatRp.currencySymbol = "Rp. "
            kursIndonesia.maximumFractionDigits = 0 //remove .00
            kursIndonesia.decimalFormatSymbols = formatRp
            return kursIndonesia.format(price)
        }
        return "0"
    }
}