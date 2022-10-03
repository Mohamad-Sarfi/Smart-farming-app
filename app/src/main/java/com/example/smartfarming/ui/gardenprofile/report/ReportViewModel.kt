package com.example.smartfarming.ui.gardenprofile.report

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartfarming.data.repositories.garden.GardenRepo
import com.example.smartfarming.utils.PersianCalender
import java.lang.IllegalArgumentException

class ReportViewModel(val repo : GardenRepo) : ViewModel() {

    private val thisYear = PersianCalender.getShamsiDateMap()["year"].toString()

    val selectedYear = mutableStateOf(thisYear)


}

class ReportViewModelFactory(val repo: GardenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReportViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}