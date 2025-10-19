package com.example.userregistrationmodel2

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class ActivityMain : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Function for navigation screens with background white
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                AppNavigation()
            }
        }
    }
}

// Controller for navigation screens w
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("register") {
            RegisterScreen(navController)
        }
        composable("home") {
            HomeScreen(navController)
        }
    }
}

//Function for fields email and password
@Composable
fun FieldsEmailPassword(email: String, emailChange: (String) -> Unit,
password: String, passwordChange: (String) -> Unit) {

    OutlinedTextField(
        value = email,
        onValueChange = { emailChange (it)},
        label = { Text("Email") },
        leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
    )

    OutlinedTextField(
        value = password,
        onValueChange = { passwordChange (it) },
        label = { Text("Password") },
        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
    )
}

//Register or login witch account Google
@Composable
fun GoogleLoginRegister(navController: NavHostController, context: Context) {
    val auth = FirebaseAuth.getInstance()

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            FirebaseAuth.getInstance().signInWithCredential(credential)
                .addOnCompleteListener { t ->
                    if (t.isSuccessful) {
                        Toast.makeText(context, "Continue with Google success!", Toast.LENGTH_SHORT).show()
                        navController.navigate("home")
                    } else {
                        Toast.makeText(context, "Continue with Google failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        } catch (e: ApiException) {
            Toast.makeText(context, "Continue with Google failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    Button(
        onClick = {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("509304447135-8br64glg5jckcc6v5e8j0rm0cq6952d2.apps.googleusercontent.com") // Coloque seu client ID aqui
                .requestEmail()
                .build()
            val googleSignInClient = GoogleSignIn.getClient(context, gso)
            googleSignInClient.signOut().addOnCompleteListener {
                launcher.launch(googleSignInClient.signInIntent)
            }
        },
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text("Continue with Google", color = Color.White)
    }
}

// Screen of login
@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val signUp = "Sign up"

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

        FieldsEmailPassword(
            email = email, emailChange = { email = it },
            password = password, passwordChange = { password = it }
        )

        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please, fill in all fields.", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Login successfully!", Toast.LENGTH_SHORT).show()
                            navController.navigate("home")
                        } else {
                            Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3))
        ) {
            Text("Sign in", color = Color.White)
        }

        GoogleLoginRegister(navController = navController, context = context)

        Text(
            text = buildAnnotatedString {
                append("Don't have an account? ")
                withStyle(style = SpanStyle(color = Color.Cyan)) {
                    append(signUp)
                }
            },
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

// Screen of register
@Composable
fun RegisterScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val context = LocalContext.current
    val signIn = "Sign in"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            color = Color.Gray,
            fontSize = 20.sp
        )
        Text(
            text = "Sign up your account",
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 40.dp)
        )

        FieldsEmailPassword(
            email = email, emailChange = { email = it },
            password = password, passwordChange = { password = it }
        )


        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Please, fill in all fields.", Toast.LENGTH_SHORT).show()
                    return@Button
                }
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Register successfully!", Toast.LENGTH_SHORT).show()
                            navController.navigate("login")
                        } else {
                            Toast.makeText(context, "Register failed: ${task.exception?.message}",Toast.LENGTH_SHORT).show()
                        }
                    }
            },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF424242))
        ) {
            Text("Sign up", color = Color.White)
        }

        GoogleLoginRegister(navController = navController, context = context)

        Text(
            text = buildAnnotatedString {
                append("Have an account? ")
                withStyle(style = SpanStyle(color = Color.Cyan)) {
                    append(signIn)
                }
            },
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