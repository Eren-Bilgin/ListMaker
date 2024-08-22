package com.yourcompany.listmaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.yourcompany.listmaker.navigation.AppNavHost
import com.yourcompany.listmaker.ui.theme.ListMakerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ListMakerTheme {
                AppNavHost()
            }
        }
    }
}
