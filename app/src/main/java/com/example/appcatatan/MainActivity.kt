package com.example.appcatatan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.appcatatan.presentation.bookmark.BookmarkViewModel
import com.example.appcatatan.presentation.detail.DetailAssistedFactory
import com.example.appcatatan.presentation.home.HomeViewModel
import com.example.appcatatan.presentation.login.LoginScreen
import com.example.appcatatan.presentation.navigation.NoteNavigation
import com.example.appcatatan.presentation.navigation.Screens
import com.example.appcatatan.presentation.navigation.navigateToSingleTop
import com.example.appcatatan.ui.theme.NoteAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var assistedFactory: DetailAssistedFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    NoteApp()
                }
            }
        }
    }

    @Composable
    fun NoteApp() {
        val isLoggedIn = rememberSaveable { mutableStateOf(false) }
        if (isLoggedIn.value) {
            NoteAppContent()
        } else {
            LoginScreen { isLoggedIn.value = true }
        }
    }

    @Composable
    fun NoteAppContent() {
        val homeViewModel: HomeViewModel = viewModel()
        val bookmarkViewModel: BookmarkViewModel = viewModel()
        val navController = rememberNavController()
        var currentTab by remember { mutableStateOf(TabScreen.Home) }
        Scaffold(
            bottomBar = {
                BottomAppBar(
                    actions = {
                        Row(
                            horizontalArrangement = Arrangement.Center
                        ) {
                            InputChip(
                                selected = currentTab == TabScreen.Home,
                                onClick = {
                                    currentTab = TabScreen.Home
                                    navController.navigateToSingleTop(
                                        route = Screens.Home.name
                                    )
                                },
                                label = {
                                    Text(text = "Home")
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Home,
                                        contentDescription = "Home Icon"
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.Companion.size(12.dp))
                            InputChip(
                                selected = currentTab == TabScreen.Bookmark,
                                onClick = {
                                    currentTab = TabScreen.Bookmark
                                    navController.navigateToSingleTop(
                                        route = Screens.Bookmark.name
                                    )
                                },
                                label = {
                                    Text(text = "Bookmark")
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Bookmark Icon"
                                    )
                                }
                            )
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                navController.navigateToSingleTop(
                                    route = Screens.Detail.name
                                )
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Icon",

                                )
                        }
                    }
                )
            }
        ) {
            NoteNavigation(
                modifier = Modifier.padding(it),
                navHostController = navController,
                homeViewModel = homeViewModel,
                bookmarkViewModel = bookmarkViewModel,
                assistedFactory = assistedFactory
            )
        }
    }
}

enum class TabScreen {
    Home, Bookmark,
}