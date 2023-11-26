package com.bharath.dailyquotesapp.feature_quotes.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController


@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
) {

    val viewModel = hiltViewModel<SettingsViewModel>()


}

@Composable
private fun Content(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    viewModel: SettingsViewModel,

    ) {

    Scaffold(
        modifier = Modifier.padding(paddingValues),
        topBar = {
            TopBarSettings()
        }
    ) { innerPad ->
        Column(
            modifier = Modifier.padding(innerPad)
        ) {


        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBarSettings() {
    TopAppBar(title = {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
            Text(text = "Settings", fontWeight = FontWeight.Bold)
        }
    })
}