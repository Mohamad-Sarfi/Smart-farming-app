package com.example.smartfarming.ui.gardenprofile.editGarden

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.smartfarming.ui.addactivities.ui.theme.LightBackground
import com.example.smartfarming.ui.common_composables.CommonTopBar

@Composable
fun EditScreen(){

    Scaffold(
        Modifier.fillMaxSize(),
        backgroundColor = LightBackground,
        topBar = { CommonTopBar(title = "ویرایش باغ", icon = Icons.Default.Eco)}
    ) {

    }

}