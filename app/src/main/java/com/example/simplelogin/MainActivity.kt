// MainActivity.kt
package com.example.simplelogin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplelogin.ui.theme.SimpleLoginTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleLoginTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    val context = LocalContext.current

    // state untuk field
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // state untuk toggle visibility password
    var passwordVisible by remember { mutableStateOf(false) }

    // state untuk pesan error / status
    var message by remember { mutableStateOf<String?>(null) }

    // Contoh "string comparable" sederhana:
    // kita bandingkan username dan password dengan nilai target (hard-coded contoh)
    val validUsername = "user"    // ganti sesuai kebutuhan
    val validPassword = "1234"   // ganti sesuai kebutuhan

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Halaman Login", fontSize = 26.sp, modifier = Modifier.padding(bottom = 24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val iconDescription = if (passwordVisible) "Sembunyikan password" else "Tampilkan password"
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    if (passwordVisible) {
                        Icon(imageVector = Icons.Default.VisibilityOff, contentDescription = iconDescription)
                    } else {
                        Icon(imageVector = Icons.Default.Visibility, contentDescription = iconDescription)
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Perbandingan string (== di Kotlin memeriksa kesetaraan isi)
                if (username == validUsername && password == validPassword) {
                    message = "Login berhasil!"
                    Toast.makeText(context, "Login berhasil", Toast.LENGTH_SHORT).show()
                } else {
                    message = "Username atau password salah."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(12.dp))

        // tampilkan pesan status/error jika ada
        message?.let {
            Text(text = it, color = if (it.contains("berhasil", ignoreCase = true)) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error)
        }
    }
}
