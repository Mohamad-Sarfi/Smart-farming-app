package com.example.smartfarming.ui.main_screen.bottom_navigation

import android.graphics.drawable.Icon
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Default
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Inventory2
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.rounded.Eco
import androidx.compose.material.icons.rounded.Grass
import androidx.compose.material.icons.rounded.Park
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.smartfarming.R

sealed class NavItem(
    @StringRes val title : Int,
    val icon : ImageVector,
    val navRoute : String
) {
    object Home : NavItem(R.string.home, Icons.Outlined.Home , NAV_HOME)
    object Garden : NavItem(R.string.orchard, Icons.Outlined.Eco , NAV_GARDENS)
    object Harvest : NavItem(R.string.harvest, Icons.Outlined.Inventory2, NAV_HARVEST)
    object Profile : NavItem(R.string.user, Icons.Outlined.Person, NAV_PROFILE)

}
