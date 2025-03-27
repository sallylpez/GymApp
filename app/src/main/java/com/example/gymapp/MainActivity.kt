package com.example.gymapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.gymapp.model.User
import com.example.gymapp.ui.screens.RecommendationScreen
import com.example.gymapp.ui.screens.UserFormScreen
import com.example.gymapp.ui.theme.GymAppTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GymAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var showRecommendations by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        userViewModel.currentUser.collect { user ->
                            showRecommendations = user != null
                        }
                    }

                    if (showRecommendations) {
                        userViewModel.currentUser.value?.let { user ->
                            RecommendationScreen(
                                user = user,
                                onBackClick = {
                                    showRecommendations = false
                                    userViewModel.clearCurrentUser()
                                }
                            )
                        }
                    } else {
                        UserFormScreen(
                            onUserSubmit = { user ->
                                userViewModel.setCurrentUser(user)
                                showRecommendations = true
                            }
                        )
                    }
                }
            }
        }
    }
}