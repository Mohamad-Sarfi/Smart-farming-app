package com.example.smartfarming.ui.gardenprofile.report.reportscreens.workers

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.smartfarming.data.repositories.garden.GardenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkersUsedViewModel @Inject constructor(repo: GardenRepo): ViewModel() {
    val number = mutableStateOf(108)


}