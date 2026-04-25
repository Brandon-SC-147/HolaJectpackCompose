package com.example.hola

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hola.ui.theme.HOLATheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HelloComposeForm() //Text("Hola Mundo, ESAN")
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelloComposeForm(){
    //Text("Hola Mundo, ESAN")
    var name by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedDate = datePickerState.selectedDateMillis
                    if (selectedDate != null) {
                        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                        sdf.timeZone = TimeZone.getTimeZone("UTC")
                        birthDate = sdf.format(Date(selectedDate))
                    }
                    showDatePicker = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {Text("Hola ESAN")})
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Bienvenido a Jetpack Compose")
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = dni,
                onValueChange = { input ->
                    // Solo permite números y limita a 8 dígitos (formato estándar DNI)
                    if (input.all { it.isDigit() } && input.length <= 8) {
                        dni = input
                    }
                },
                label = { Text("DNI") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Box(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = birthDate,
                    onValueChange = { },
                    label = { Text("Fecha de Nacimiento") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clickable { showDatePicker = true }
                )
            }
            Button(
                onClick = {},
                enabled = name.isNotEmpty() && dni.length == 8 && birthDate.isNotEmpty(),
                modifier = Modifier.fillMaxWidth()

            ){
                Text("Enviar")
             }

        }
    }
}




