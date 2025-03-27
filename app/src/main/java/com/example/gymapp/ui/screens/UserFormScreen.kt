package com.example.gymapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.gymapp.model.Objetivo
import com.example.gymapp.model.Sexo
import com.example.gymapp.model.User

@Composable
fun UserFormScreen(
    onUserSubmit: (User) -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var peso by remember { mutableStateOf("") }
    var estatura by remember { mutableStateOf("") }
    var sexoSeleccionado by remember { mutableStateOf<Sexo?>(null) }
    var objetivoSeleccionado by remember { mutableStateOf<Objetivo?>(null) }
    var tieneEnfermedad by remember { mutableStateOf(false) }
    var enfermedadRestriccion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Información Personal",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = edad,
                onValueChange = { edad = it },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = peso,
                onValueChange = { peso = it },
                label = { Text("Peso (kg)") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = estatura,
                onValueChange = { estatura = it },
                label = { Text("Estatura (cm)") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Sexo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Sexo.entries.forEach { sexo ->
                    FilterChip(
                        selected = sexoSeleccionado == sexo,
                        onClick = { sexoSeleccionado = sexo },
                        label = { Text(sexo.name) }
                    )
                }
            }

            Text(
                text = "Objetivo",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Objetivo.entries.forEach { objetivo ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = objetivoSeleccionado == objetivo,
                            onClick = { objetivoSeleccionado = objetivo }
                        )
                        Text(objetivo.name)
                    }
                }
            }

            Text(
                text = "Consideraciones Especiales",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = tieneEnfermedad,
                    onCheckedChange = { tieneEnfermedad = it }
                )
                Text("¿Tienes alguna enfermedad o restricción?")
            }

            if (tieneEnfermedad) {
                OutlinedTextField(
                    value = enfermedadRestriccion,
                    onValueChange = { enfermedadRestriccion = it },
                    label = { Text("Describe tu enfermedad o restricción") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
            }
        }

        Button(
            onClick = {
                if (nombre.isNotBlank() && edad.isNotBlank() && peso.isNotBlank() && 
                    estatura.isNotBlank() && sexoSeleccionado != null && 
                    objetivoSeleccionado != null) {
                    onUserSubmit(
                        User(
                            nombre = nombre,
                            edad = edad.toIntOrNull() ?: 0,
                            peso = peso.toFloatOrNull() ?: 0f,
                            estatura = estatura.toFloatOrNull() ?: 0f,
                            sexo = sexoSeleccionado!!,
                            objetivo = objetivoSeleccionado!!,
                            enfermedadRestriccion = if (tieneEnfermedad) enfermedadRestriccion else ""
                        )
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Continuar")
        }
    }
} 