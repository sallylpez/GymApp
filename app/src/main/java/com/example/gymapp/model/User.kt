package com.example.gymapp.model

data class User(
    val nombre: String,
    val edad: Int,
    val peso: Float,
    val estatura: Float,
    val sexo: Sexo,
    val objetivo: Objetivo,
    val enfermedadRestriccion: String
) 