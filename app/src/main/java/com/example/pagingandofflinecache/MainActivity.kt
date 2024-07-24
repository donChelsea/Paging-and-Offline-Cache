package com.example.pagingandofflinecache

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.pagingandofflinecache.presentation.CharacterScreen
import com.example.pagingandofflinecache.presentation.CharacterViewModel
import com.example.pagingandofflinecache.ui.theme.PagingAndOfflineCacheTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PagingAndOfflineCacheTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    val viewModel = hiltViewModel<CharacterViewModel>()
                    val characters = viewModel.beerPagingFlow.collectAsLazyPagingItems()
                    CharacterScreen(
                        characters = characters,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}