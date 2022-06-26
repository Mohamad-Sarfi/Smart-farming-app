package com.example.smartfarming.ui.tasks_notification

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.smartfarming.ui.authentication.ui.theme.SmartFarmingTheme

class TasksNotificationActivity  : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SmartFarmingTheme() {
                TasksNotificationCompose()
            }
        }

    }
}

