# Login & Register Model App 2 🔐📲 (Jetpack Compose)

[![Kotlin](https://img.shields.io/badge/Language-Kotlin-orange?logo=kotlin)](https://kotlinlang.org/)  
[![Android Studio](https://img.shields.io/badge/IDE-Android_Studio-brightgreen?logo=android-studio)](https://developer.android.com/studio)  
[![Firebase](https://img.shields.io/badge/Firebase-FFCA28?logo=firebase&logoColor=white)](https://firebase.google.com/docs/auth)  
[![License](https://img.shields.io/badge/License-MIT-blue)](LICENSE)  

---

## Project 🎯
This Android app provides a **Login & Register Model App 2** using **Firebase Authentication (Email/Password)**, implemented entirely in **Jetpack Compose**.  
It includes Login Screen, Register Screen, and Home Dashboard. The app is designed with a **modern, responsive UI**, interactive clickable links, reusable components, and Compose-native layouts. Perfect for learning authentication flows and modern Android UI best practices. <br>
The reverse engineering was done with the **Login & Register Model App (XML)**: (https://github.com/Luke02su/AppAndroidLogin-RegisterModel). However, now exist the presence of register/login with **Google Authenticator**.

---

## App Layout 🎨

### Login Screen
- Email and Password input fields with **icons**, **padding**, and **rounded corners**.  
- **Sign In button**: blue (#2196F3), full-width, rounded corners (10dp).  
- **Clickable "Sign up" link** to navigate to Register screen.  
- Compose replaces XML layouts (`LinearLayout`, `ConstraintLayout`) with `Column` and `Modifier`-based design
- **Continue with Google** with same design, but color red.

### Register Screen
- Similar styling to Login screen.  
- **Sign Up button** with the same design, but color is gray .
- **Clickable "Sign in" link** to navigate to Login screen.  
- Firebase handles **account creation and validation**.
- **Continue with Google** with same design, but color red.

### Home Screen
- Placeholder dashboard for post-login content.  
- Fully **responsive**.  
- Ready to add **navigation drawer, tabs, or app features**.

<p align="center">
   <img width="300" height="600" alt="Login Screen" src="https://github.com/user-attachments/assets/cc9c9682-c46a-4465-86bc-431c6a613174" />
   <img width="300" height="600" alt="Register Screen" src="https://github.com/user-attachments/assets/092d7eaf-7bd6-4796-be9e-b2a5dc5a34c3" />
   <img width="300" height="600" alt="Home Screen" src="https://github.com/user-attachments/assets/d7182c03-57c4-415c-b5ff-79f3e89b339d" />
</p>

---

## Features ✨
| Feature | Description |
|---------|-------------|
| Login/Register | Email/password authentication and Google authentication using Firebase |
| Input Validation | Check for empty fields, correct email format |
| Toast Messages | Descriptive feedback for success/error |
| Clickable Links | Navigate between Login/Register screens using Compose Navigation |
| Custom Drawables | Rounded corners, padding, icons handled with Compose modifiers |
| Styled Buttons | Blue (#2196F3) and gray (#424242) buttons with white text, corner radius 10dp |
| Responsive | Works on phones and tablets |
| Modular Design | Screens as separate `@Composable` functions |
| Home Screen | Placeholder for navigation drawer, profile info, or tabs |

---

## Firebase Integration 🔐
- **Email/Password Authentication**  
- **User creation, login, and error handling**  
- Ready for future expansion:
  - Remember login  
  - Password reset  
  - Email verification  
  - Social OAuth login (Google, Facebook, etc.)  

**Compose register using email example**
```kotlin
auth.createUserWithEmailAndPassword(email, password)
    .addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(context, "Register successfully!", Toast.LENGTH_SHORT).show()
            navController.navigate("login")
        } else {
            Toast.makeText(context, "Register failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
        }
    }
```

**Compose login using email example**
```kotlin

auth.signInWithEmailAndPassword(email, password)
    .addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Toast.makeText(context, "Login successfully!", Toast.LENGTH_SHORT).show()
            navController.navigate("home")
        } else {
            Toast.makeText(context, "Login failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
        }
    }
```

---

## Technologies Used 🤖🍏

- Programming Language: Kotlin
- Development Environment: Android Studio
- Firebase Authentication: Email/Password login
- UI Components: Jetpack Compose (Column, Text, OutlinedTextField, Button, Icon, Text)
- Custom Styles: Rounded corners (10dp), padding, icons via leadingIcon, clickable spans via AnnotatedString
- Target Platform: Android Mobile Devices

---

## Best Practices ✅

- Modular and maintainable Compose screen structure
- Reusable @Composable components for buttons, text fields, and links
- Input validation for security and UX
- Edge-to-edge layout for modern immersive design
- Responsive and adaptive to different screen sizes
- Clickable spans for interactive links
- Navigation handled with Navigation Compose, replacing Intents

---

## Code Author 💻👨‍💻
```kotlin
fun main() {
    println("Code by Lucas Samuel Dias!")
    println("Welcome to the Login & Register Model App using Jetpack Compose!")
}
```





