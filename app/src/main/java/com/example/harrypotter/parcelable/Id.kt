package com.example.harrypotter.parcelable
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/* Salvar apenas o Id (personagem ou feitico) para carregar informacoes da API na proxima activity. */

@Parcelize
data class Id(
    val id: String
): Parcelable
