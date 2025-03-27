package com.example.gymapp.model

enum class Sexo {
    MASCULINO,
    FEMENINO;

    companion object {
        fun fromString(value: String): Sexo {
            return entries.find { it.name == value } ?: MASCULINO
        }
    }
} 