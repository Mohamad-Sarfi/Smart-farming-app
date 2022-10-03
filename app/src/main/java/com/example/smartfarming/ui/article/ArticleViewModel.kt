package com.example.smartfarming.ui.article

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.smartfarming.data.repositories.garden.GardenRepo
import java.lang.IllegalArgumentException

class ArticleViewModel(val repo : GardenRepo) : ViewModel() {

}

class ArticleViewModelFactory(private val repo: GardenRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ArticleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ArticleViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}