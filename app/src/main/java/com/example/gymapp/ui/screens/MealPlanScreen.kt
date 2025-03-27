package com.example.gymapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.model.Objetivo
import com.example.gymapp.model.User
import com.example.gymapp.model.Sexo

@Composable
fun MealPlanScreen(
    user: User,
    onBackClick: () -> Unit,
    onContinueClick: (Int) -> Unit
) {
    var numeroComidas by remember { mutableStateOf(5) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    
    // Calcular padding dinámico basado en el tamaño de la pantalla
    val horizontalPadding = (screenWidth * 0.05f).toInt().dp
    val verticalPadding = (screenHeight * 0.02f).toInt().dp
    val cardSpacing = (screenHeight * 0.02f).toInt().dp

    // Calcular TMB (Tasa Metabólica Basal)
    val tmb = when (user.sexo) {
        Sexo.MASCULINO -> (10 * user.peso) + (6.25 * user.estatura) - (5 * user.edad) + 5
        Sexo.FEMENINO -> (10 * user.peso) + (6.25 * user.estatura) - (5 * user.edad) - 161
    }

    // Factor de actividad según el objetivo
    val factorActividad = when (user.objetivo) {
        Objetivo.PERDIDA_DE_PESO -> 1.2
        Objetivo.GANANCIA_MUSCULAR -> 1.4
        Objetivo.FUERZA -> 1.4
        Objetivo.RESISTENCIA -> 1.3
        Objetivo.TONIFICACION -> 1.3
        Objetivo.SALUD_GENERAL -> 1.2
    }

    // Calcular calorías totales
    val caloriasTotales = tmb * factorActividad

    // Calcular calorías por comida
    val caloriasPorComida = caloriasTotales / numeroComidas

    // Calcular macronutrientes según el objetivo
    val (proteinas, carbohidratos, grasas) = when (user.objetivo) {
        Objetivo.PERDIDA_DE_PESO -> Triple(0.3, 0.4, 0.3)
        Objetivo.GANANCIA_MUSCULAR -> Triple(0.3, 0.5, 0.2)
        Objetivo.FUERZA -> Triple(0.3, 0.5, 0.2)
        Objetivo.RESISTENCIA -> Triple(0.2, 0.6, 0.2)
        Objetivo.TONIFICACION -> Triple(0.3, 0.4, 0.3)
        Objetivo.SALUD_GENERAL -> Triple(0.25, 0.45, 0.3)
    }

    // Calcular gramos de macronutrientes por comida
    val proteinasPorComida = (caloriasPorComida * proteinas / 4).toInt()
    val carbohidratosPorComida = (caloriasPorComida * carbohidratos / 4).toInt()
    val grasasPorComida = (caloriasPorComida * grasas / 9).toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontalPadding, vertical = verticalPadding)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(cardSpacing)
    ) {
        Text(
            text = "Plan de Comidas Personalizado",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontSize = (screenWidth * 0.06f).toInt().sp
            ),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = cardSpacing)
        )

        // Tarjeta de TMB y Calorías
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontalPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "📊 Metabolismo y Calorías",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = (screenWidth * 0.045f).toInt().sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "TMB: ${tmb.toInt()} kcal",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = (screenWidth * 0.04f).toInt().sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Calorías totales: ${caloriasTotales.toInt()} kcal",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = (screenWidth * 0.04f).toInt().sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "Calorías por comida: ${caloriasPorComida.toInt()} kcal",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = (screenWidth * 0.04f).toInt().sp
                    ),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Tarjeta de Macronutrientes
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontalPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "🥩 Macronutrientes por Comida",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = (screenWidth * 0.045f).toInt().sp
                    ),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Proteínas: ${proteinasPorComida}g",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = (screenWidth * 0.04f).toInt().sp
                    ),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Carbohidratos: ${carbohidratosPorComida}g",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = (screenWidth * 0.04f).toInt().sp
                    ),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "Grasas: ${grasasPorComida}g",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = (screenWidth * 0.04f).toInt().sp
                    ),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }

        // Tarjeta de Selección de Comidas
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(horizontalPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "🍽️ Número de Comidas",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = (screenWidth * 0.045f).toInt().sp
                    ),
                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                    fontWeight = FontWeight.Bold
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { if (numeroComidas > 3) numeroComidas-- },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Reducir comidas",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Text(
                        text = "$numeroComidas comidas",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = (screenWidth * 0.045f).toInt().sp
                        ),
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                    
                    IconButton(
                        onClick = { if (numeroComidas < 6) numeroComidas++ },
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Aumentar comidas",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Botones de navegación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onBackClick,
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Volver")
            }
            
            Button(
                onClick = { onContinueClick(numeroComidas) },
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Continuar")
            }
        }
    }
} 