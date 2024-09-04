package com.mit.shop.view.nav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.mit.shop.SessionManager

@Composable
fun BottomNavigationBar(navController: NavController) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }

    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = navController.currentDestination?.route == "home_screen",
            onClick = {
                navController.navigate("home_screen")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = navController.currentDestination?.route == "cart_screen",
            onClick = {
                navController.navigate("cart_screen")
            }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Account") },
            label = { Text("Account") },
            selected = false,
            onClick = {
                showLogoutDialog = true
            }
        )
    }

    if (showLogoutDialog) {
        LogoutConfirmationDialog(
            onConfirm = {
                showLogoutDialog = false
                sessionManager.clearSession()
                navController.navigate("sign_in") {
                    popUpTo("sign_in") { inclusive = true }
                }
            },
            onDismiss = {
                showLogoutDialog = false
            }
        )
    }
}



fun logout(navController: NavController, sessionManager: SessionManager) {
    sessionManager.clearSession()
    navController.navigate("sign_in") {
        popUpTo("sign_in") { inclusive = true }
    }
}

@Composable
fun LogoutConfirmationDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Logout") },
        text = { Text("Are you sure you want to log out?") },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("No")
            }
        }
    )
}