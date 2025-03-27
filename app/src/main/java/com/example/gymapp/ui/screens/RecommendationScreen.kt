package com.example.gymapp.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymapp.model.Objetivo
import com.example.gymapp.model.User
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.properties.TextAlignment
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecommendationScreen(
    user: User,
    onBackClick: () -> Unit
) {
    var showMealPlan by remember { mutableStateOf(true) }
    var numeroComidas by remember { mutableIntStateOf(5) }
    val context = LocalContext.current

    if (showMealPlan) {
        MealPlanScreen(
            user = user,
            onBackClick = onBackClick,
            onContinueClick = { comidas ->
                numeroComidas = comidas
                showMealPlan = false
            }
        )
    } else {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        
        val rutina = when (user.objetivo) {
            Objetivo.PERDIDA_DE_PESO -> """
            • 30-45 minutos de cardio (caminadora, bicicleta, elíptica)
            • 3 series de 12-15 repeticiones por ejercicio
            • Circuitos de alta intensidad
            • Descanso de 30-45 segundos entre series
            """.trimIndent()
            Objetivo.GANANCIA_MUSCULAR -> """
            • 4-5 series de 8-12 repeticiones
            • Ejercicios compuestos (sentadillas, press de banca, peso muerto)
            • Descanso de 60-90 segundos entre series
            • 3-4 días de entrenamiento por semana
            """.trimIndent()
            Objetivo.FUERZA -> """
            • 5 series de 5 repeticiones
            • Ejercicios básicos con peso progresivo
            • Descanso de 2-3 minutos entre series
            • 3 días de entrenamiento por semana
            """.trimIndent()
            Objetivo.RESISTENCIA -> """
            • 2-3 series de 15-20 repeticiones
            • Circuitos de resistencia
            • Descanso de 30 segundos entre series
            • 4-5 días de entrenamiento por semana
            """.trimIndent()
            Objetivo.TONIFICACION -> """
            • 3 series de 12-15 repeticiones
            • Combinación de cardio y fuerza
            • Descanso de 45-60 segundos entre series
            • 4 días de entrenamiento por semana
            """.trimIndent()
            Objetivo.SALUD_GENERAL -> """
            • 3 series de 10-12 repeticiones
            • Ejercicios variados
            • Descanso de 45-60 segundos entre series
            • 3-4 días de entrenamiento por semana
            """.trimIndent()
        }

        val dieta = when (user.objetivo) {
            Objetivo.PERDIDA_DE_PESO -> """
            • Desayuno:
              - 1 taza de avena (30g)
              - 1 taza de leche descremada (240ml)
              - 1 plátano mediano
              - 1 cucharada de chía (15g)
            • Media mañana:
              - 1 manzana mediana
              - 10 almendras (15g)
            • Almuerzo:
              - 1 taza de arroz integral (200g)
              - 1/2 taza de frijoles (100g)
              - 100g de pechuga de pollo
              - 2 tazas de ensalada de verduras
              - 1 cucharada de aceite de oliva
            • Merienda:
              - 1 taza de yogurt griego (200g)
              - 1/2 taza de fresas
            • Cena:
              - 120g de pescado al horno
              - 1 taza de brócoli al vapor
              - 1/2 taza de zanahorias
              - 1 tortilla de maíz
            """.trimIndent()
            Objetivo.GANANCIA_MUSCULAR -> """
            • Desayuno:
              - 3 huevos enteros
              - 1 taza de avena (30g)
              - 1 plátano mediano
              - 1 taza de leche entera (240ml)
            • Media mañana:
              - 1 taza de fruta picada
              - 20g de proteína en polvo
              - 15g de frutos secos
            • Almuerzo:
              - 1 taza de arroz integral (200g)
              - 1/2 taza de frijoles (100g)
              - 150g de pechuga de pollo
              - 2 tazas de ensalada
              - 1 aguacate mediano
            • Merienda:
              - 1 taza de yogurt griego (200g)
              - 1/2 taza de granola
              - 1 taza de fruta
            • Cena:
              - 150g de atún
              - 1 taza de pasta integral
              - 2 tazas de verduras al vapor
              - 2 tortillas de maíz
            """.trimIndent()
            Objetivo.FUERZA -> """
            • Desayuno:
              - 4 huevos enteros
              - 1 taza de avena (30g)
              - 1 plátano mediano
              - 1 taza de leche entera (240ml)
            • Media mañana:
              - 1 taza de fruta
              - 30g de proteína en polvo
              - 20g de frutos secos
            • Almuerzo:
              - 1 taza de arroz integral (200g)
              - 1/2 taza de frijoles (100g)
              - 200g de carne magra
              - 2 tazas de ensalada
              - 1 aguacate mediano
            • Merienda:
              - 1 taza de yogurt griego (200g)
              - 1/2 taza de granola
              - 1 taza de fruta
            • Cena:
              - 180g de pescado
              - 1 taza de quinoa
              - 2 tazas de verduras
              - 2 tortillas de maíz
            """.trimIndent()
            Objetivo.RESISTENCIA -> """
            • Desayuno:
              - 1 taza de avena (30g)
              - 1 taza de leche descremada (240ml)
              - 1 plátano mediano
              - 1 cucharada de miel
            • Media mañana:
              - 1 taza de fruta
              - 1 taza de yogurt natural
            • Almuerzo:
              - 1 taza de arroz integral (200g)
              - 1/2 taza de frijoles (100g)
              - 100g de pechuga de pollo
              - 2 tazas de ensalada
              - 1 cucharada de aceite de oliva
            • Merienda:
              - 1 taza de fruta
              - 15g de frutos secos
            • Cena:
              - 120g de pescado
              - 1 taza de quinoa
              - 2 tazas de verduras
              - 1 tortilla de maíz
            """.trimIndent()
            Objetivo.TONIFICACION -> """
            • Desayuno:
              - 2 huevos enteros
              - 1 taza de avena (30g)
              - 1 taza de leche descremada (240ml)
              - 1 plátano mediano
            • Media mañana:
              - 1 taza de fruta
              - 15g de frutos secos
            • Almuerzo:
              - 1 taza de arroz integral (200g)
              - 1/2 taza de frijoles (100g)
              - 120g de pechuga de pollo
              - 2 tazas de ensalada
              - 1 cucharada de aceite de oliva
            • Merienda:
              - 1 taza de yogurt griego (200g)
              - 1/2 taza de fruta
            • Cena:
              - 150g de pescado
              - 1 taza de quinoa
              - 2 tazas de verduras
              - 1 tortilla de maíz
            """.trimIndent()
            Objetivo.SALUD_GENERAL -> """
            • Desayuno:
              - 2 huevos enteros
              - 1 taza de avena (30g)
              - 1 taza de leche descremada (240ml)
              - 1 plátano mediano
            • Media mañana:
              - 1 taza de fruta
              - 15g de frutos secos
            • Almuerzo:
              - 1 taza de arroz integral (200g)
              - 1/2 taza de frijoles (100g)
              - 120g de pechuga de pollo
              - 2 tazas de ensalada
              - 1 cucharada de aceite de oliva
            • Merienda:
              - 1 taza de yogurt griego (200g)
              - 1/2 taza de fruta
            • Cena:
              - 150g de pescado
              - 1 taza de quinoa
              - 2 tazas de verduras
              - 1 tortilla de maíz
            """.trimIndent()
        }

        val ejerciciosSugeridos = when (user.objetivo) {
            Objetivo.PERDIDA_DE_PESO -> """
            Ejercicios Cardio:
            • Caminadora: 20-30 minutos a ritmo moderado
            • Bicicleta estática: 20-30 minutos
            • Elíptica: 20-30 minutos
            • Saltar la cuerda: 3 series de 2 minutos

            Ejercicios de Fuerza:
            • Sentadillas: 3 series de 15 repeticiones
            • Flexiones de brazos: 3 series de 12 repeticiones
            • Plancha: 3 series de 45 segundos
            • Zancadas: 3 series de 12 repeticiones por pierna
            """.trimIndent()
            Objetivo.GANANCIA_MUSCULAR -> """
            Ejercicios Compuestos:
            • Sentadillas con barra: 4 series de 8-12 repeticiones
            • Press de banca: 4 series de 8-12 repeticiones
            • Peso muerto: 4 series de 8-12 repeticiones
            • Remo con barra: 4 series de 8-12 repeticiones

            Ejercicios de Aislamiento:
            • Extensiones de tríceps: 3 series de 12-15 repeticiones
            • Curl de bíceps: 3 series de 12-15 repeticiones
            • Elevaciones laterales: 3 series de 12-15 repeticiones
            • Extensiones de pierna: 3 series de 12-15 repeticiones
            """.trimIndent()
            Objetivo.FUERZA -> """
            Ejercicios Principales:
            • Sentadillas: 5 series de 5 repeticiones
            • Press de banca: 5 series de 5 repeticiones
            • Peso muerto: 5 series de 5 repeticiones
            • Press militar: 5 series de 5 repeticiones

            Ejercicios Accesorios:
            • Remo con barra: 3 series de 8 repeticiones
            • Dominadas: 3 series de 5 repeticiones
            • Zancadas: 3 series de 8 repeticiones por pierna
            • Plancha: 3 series de 60 segundos
            """.trimIndent()
            Objetivo.RESISTENCIA -> """
            Circuitos de Resistencia:
            • Burpees: 3 series de 20 repeticiones
            • Mountain climbers: 3 series de 30 segundos
            • Jumping jacks: 3 series de 30 segundos
            • Escaladores: 3 series de 30 segundos

            Ejercicios de Resistencia:
            • Carrera continua: 20-30 minutos
            • Natación: 20-30 minutos
            • Bicicleta: 20-30 minutos
            • Circuitos HIIT: 20-30 minutos
            """.trimIndent()
            Objetivo.TONIFICACION -> """
            Ejercicios de Fuerza:
            • Sentadillas: 3 series de 15 repeticiones
            • Flexiones: 3 series de 12 repeticiones
            • Zancadas: 3 series de 12 repeticiones por pierna
            • Plancha: 3 series de 45 segundos

            Ejercicios de Cardio:
            • Caminadora: 15-20 minutos
            • Bicicleta: 15-20 minutos
            • Elíptica: 15-20 minutos
            • Circuitos de alta intensidad: 15-20 minutos
            """.trimIndent()
            Objetivo.SALUD_GENERAL -> """
            Ejercicios Variados:
            • Caminata rápida: 20-30 minutos
            • Yoga: 20-30 minutos
            • Pilates: 20-30 minutos
            • Tai Chi: 20-30 minutos

            Ejercicios de Fuerza:
            • Sentadillas: 3 series de 12 repeticiones
            • Flexiones: 3 series de 10 repeticiones
            • Plancha: 3 series de 30 segundos
            • Estiramientos: 10-15 minutos
            """.trimIndent()
        }

        val pesoKg = user.peso
        val proteinaMin = when (user.objetivo) {
            Objetivo.PERDIDA_DE_PESO -> pesoKg * 1.6
            Objetivo.GANANCIA_MUSCULAR -> pesoKg * 2.2
            Objetivo.FUERZA -> pesoKg * 2.0
            Objetivo.RESISTENCIA -> pesoKg * 1.4
            Objetivo.TONIFICACION -> pesoKg * 1.8
            Objetivo.SALUD_GENERAL -> pesoKg * 1.6
        }
        val proteinaMax = when (user.objetivo) {
            Objetivo.PERDIDA_DE_PESO -> pesoKg * 2.2
            Objetivo.GANANCIA_MUSCULAR -> pesoKg * 2.8
            Objetivo.FUERZA -> pesoKg * 2.6
            Objetivo.RESISTENCIA -> pesoKg * 1.8
            Objetivo.TONIFICACION -> pesoKg * 2.2
            Objetivo.SALUD_GENERAL -> pesoKg * 2.0
        }

        val requerimientosProteicos = when (user.objetivo) {
            Objetivo.PERDIDA_DE_PESO -> "1.6 - 2.2 g/kg de peso corporal"
            Objetivo.GANANCIA_MUSCULAR -> "2.2 - 2.8 g/kg de peso corporal"
            Objetivo.FUERZA -> "2.0 - 2.6 g/kg de peso corporal"
            Objetivo.RESISTENCIA -> "1.4 - 1.8 g/kg de peso corporal"
            Objetivo.TONIFICACION -> "1.8 - 2.2 g/kg de peso corporal"
            Objetivo.SALUD_GENERAL -> "1.6 - 2.0 g/kg de peso corporal"
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Plan Personalizado") },
                    navigationIcon = {
                        IconButton(onClick = onBackClick) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            val pdfFile = generarPDF(user, rutina, dieta, ejerciciosSugeridos, proteinaMin, proteinaMax, requerimientosProteicos, context)
                            if (pdfFile != null) {
                                val uri = FileProvider.getUriForFile(
                                    context,
                                    "${context.packageName}.fileprovider",
                                    pdfFile
                                )
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "application/pdf"
                                    putExtra(Intent.EXTRA_STREAM, uri)
                                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                }
                                context.startActivity(Intent.createChooser(intent, "Compartir Plan PDF"))
                            }
                        }) {
                            Icon(Icons.Default.Share, contentDescription = "Compartir PDF")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    Text(
                        text = "Plan Personalizado para ${user.nombre}",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = (screenWidth * 0.05f).toInt().sp
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )

                    if (user.enfermedadRestriccion.isNotBlank()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            ),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 12.dp
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(32.dp),
                                verticalArrangement = Arrangement.spacedBy(20.dp)
                            ) {
                                Text(
                                    text = "⚠️ Consideraciones Especiales",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = (screenWidth * 0.04f).toInt().sp
                                    ),
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = user.enfermedadRestriccion,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = (screenWidth * 0.035f).toInt().sp
                                    ),
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(
                                text = "💪 Rutina de Ejercicios",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = (screenWidth * 0.04f).toInt().sp
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = rutina,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (screenWidth * 0.035f).toInt().sp
                                ),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(
                                text = "🍽️ Plan de Alimentación",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = (screenWidth * 0.04f).toInt().sp
                                ),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = dieta,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (screenWidth * 0.035f).toInt().sp
                                ),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(
                                text = "💪 Ejercicios Sugeridos",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = (screenWidth * 0.04f).toInt().sp
                                ),
                                color = MaterialTheme.colorScheme.onTertiaryContainer,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = ejerciciosSugeridos,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (screenWidth * 0.035f).toInt().sp
                                ),
                                color = MaterialTheme.colorScheme.onTertiaryContainer
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(32.dp),
                            verticalArrangement = Arrangement.spacedBy(20.dp)
                        ) {
                            Text(
                                text = "🥩 Requerimientos Proteicos",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontSize = (screenWidth * 0.04f).toInt().sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold
                            )
                            
                            Text(
                                text = "Rango recomendado: ${proteinaMin.toInt()} - ${proteinaMax.toInt()}g de proteína por día",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontSize = (screenWidth * 0.035f).toInt().sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "($requerimientosProteicos)",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = (screenWidth * 0.03f).toInt().sp
                                ),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                val pdfFile = generarPDF(user, rutina, dieta, ejerciciosSugeridos, proteinaMin, proteinaMax, requerimientosProteicos, context)
                                if (pdfFile != null) {
                                    val uri = FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.fileprovider",
                                        pdfFile
                                    )
                                    val intent = Intent(Intent.ACTION_SEND).apply {
                                        type = "application/pdf"
                                        putExtra(Intent.EXTRA_STREAM, uri)
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                    context.startActivity(Intent.createChooser(intent, "Compartir Plan PDF"))
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("PDF")
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = {
                                val pdfFile = generarPDF(user, rutina, dieta, ejerciciosSugeridos, proteinaMin, proteinaMax, requerimientosProteicos, context)
                                if (pdfFile != null) {
                                    val uri = FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.fileprovider",
                                        pdfFile
                                    )
                                    val intent = Intent(Intent.ACTION_SEND).apply {
                                        type = "application/pdf"
                                        putExtra(Intent.EXTRA_STREAM, uri)
                                        putExtra(Intent.EXTRA_SUBJECT, "Tu Plan Personalizado de GymApp")
                                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                    }
                                    context.startActivity(Intent.createChooser(intent, "Enviar PDF por Email"))
                                }
                            },
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(Icons.Default.Email, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Email")
                        }
                    }
                }
            }
        }
    }
}

private fun generarPDF(
    user: User,
    rutina: String,
    dieta: String,
    ejerciciosSugeridos: String,
    proteinaMin: Double,
    proteinaMax: Double,
    requerimientosProteicos: String,
    context: Context
): File? {
    return try {
        val pdfFile = File(context.cacheDir, "plan_personalizado.pdf")
        val writer = PdfWriter(FileOutputStream(pdfFile))
        val pdf = PdfDocument(writer)
        val document = Document(pdf)

        // Título
        val titulo = Paragraph("Plan Personalizado para ${user.nombre}")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(20f)
        document.add(titulo)
        document.add(Paragraph(""))

        // Consideraciones Especiales
        if (user.enfermedadRestriccion.isNotBlank()) {
            document.add(Paragraph("⚠️ Consideraciones Especiales"))
                .setFontSize(16f)
                .setBold()
            document.add(Paragraph(user.enfermedadRestriccion))
            document.add(Paragraph(""))
        }

        // Rutina de Ejercicios
        document.add(Paragraph("💪 Rutina de Ejercicios"))
            .setFontSize(16f)
            .setBold()
        document.add(Paragraph(rutina))
        document.add(Paragraph(""))

        // Plan de Alimentación
        document.add(Paragraph("🍽️ Plan de Alimentación"))
            .setFontSize(16f)
            .setBold()
        document.add(Paragraph(dieta))
        document.add(Paragraph(""))

        // Ejercicios Sugeridos
        document.add(Paragraph("💪 Ejercicios Sugeridos"))
            .setFontSize(16f)
            .setBold()
        document.add(Paragraph(ejerciciosSugeridos))
        document.add(Paragraph(""))

        // Requerimientos Proteicos
        document.add(Paragraph("🥩 Requerimientos Proteicos"))
            .setFontSize(16f)
            .setBold()
        document.add(Paragraph("Rango recomendado: ${proteinaMin.toInt()} - ${proteinaMax.toInt()}g de proteína por día"))
        document.add(Paragraph("($requerimientosProteicos)"))

        document.close()
        pdf.close()
        writer.close()
        pdfFile
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
} 