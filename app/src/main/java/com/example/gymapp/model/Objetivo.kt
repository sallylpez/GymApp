package com.example.gymapp.model

enum class Objetivo {
    PERDIDA_DE_PESO,
    GANANCIA_MUSCULAR,
    FUERZA,
    RESISTENCIA,
    TONIFICACION,
    SALUD_GENERAL;

    companion object {
        fun fromString(value: String): Objetivo {
            return entries.find { it.name == value } ?: SALUD_GENERAL
        }
    }
} 