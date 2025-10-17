package com.example.userregistrationmodel2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Function for navigation screens
        setContent {
            AppNavigation()
        }
    }
}

// Controller for navigation screens
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                LoginScreen(navController)
            }
        }
        composable("register") {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                RegisterScreen(navController)
            }
        }
        composable("home") {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White
            ) {
                HomeScreen(navController)
            }
        }
    }
}

// Screen of login
@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sign In",
                color = Color.Gray,
                fontSize = 20.sp
            )

            Text(
                text = "Sign in your account",
                fontSize = 12.sp,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 40.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                singleLine = true
            )

            Button(
                onClick = {
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(context, "Login successfully!", Toast.LENGTH_SHORT)
                                    .show()
                                navController.navigate("home")
                            } else {
                                Toast.makeText(
                                    context,
                                    "Login failed: ${task.exception?.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
            ) {
                Text("Sign in", color = Color.White)
            }

            Text(
                text = "Don't have an account? Sign up",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clickable {
                        navController.navigate("register")
                    }
            )
        }
    }
}

// Screen of register
@Composable
fun RegisterScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text (
            text = "Sign Up",
            color = Color.Gray,
            fontSize = 20.sp
        )
        Text (
            text = "Sign up your account",
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 40.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            singleLine = true
        )

        Button(
            onClick = {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Register successfully!", Toast.LENGTH_SHORT).show()
                            navController.navigate("login")
                        } else {
                            Toast.makeText(context, "Register failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF424242))
        ) {
            Text("Sign up", color = Color.White)
        }

        Text(
            text = "Have an account? Sign in",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 20.dp)
                .clickable {
                    navController.navigate("login")
                }
        )
    }
}

// Screen of home
@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome! Keep programming from here...",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}