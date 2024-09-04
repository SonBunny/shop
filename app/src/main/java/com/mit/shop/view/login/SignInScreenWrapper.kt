package com.mit.shop.view.login

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.mit.shop.SessionManager
import com.mit.shop.network.account.AccountRepository

@Composable
fun SignInScreenWrapper(navController: NavController) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    val accountRepository = AccountRepository()
    var isLoading by remember { mutableStateOf(false) }

    val signIn: (String, String) -> Unit = { email, password ->
        isLoading = true
        accountRepository.authenticateUser(email, password) { authenticatedAccount ->
            isLoading = false
            if (authenticatedAccount != null) {

                sessionManager.saveUserId(authenticatedAccount.id)

                val savedUserId = sessionManager.getUserId()
                Log.d("SignInScreenWrapper", "Saved user ID: $savedUserId")



                navController.navigate("home_screen") {
                    popUpTo("home_screen") { inclusive = true }
                }
            } else {

            }
        }
    }

    SignInScreen(
        navController = navController,
        onSignIn = signIn
    )
}
