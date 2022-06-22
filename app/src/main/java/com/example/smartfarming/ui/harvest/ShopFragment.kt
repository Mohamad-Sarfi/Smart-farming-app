package com.example.smartfarming.ui.harvest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.activityViewModels
import androidx.navigation.compose.rememberNavController
import com.example.smartfarming.FarmApplication
import com.example.smartfarming.ui.addactivities.ui.theme.SmartFarmingTheme


class ShopFragment : Fragment() {

    val viewModel : HarvestViewModel by activityViewModels {
        HarvestViewModelFactory((activity?.application as FarmApplication).repo)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SmartFarmingTheme() {

                    val navController = rememberNavController()

                    HarvestNavGraph(
                        navController = navController
                    )
                }
            }
        }
    }
}