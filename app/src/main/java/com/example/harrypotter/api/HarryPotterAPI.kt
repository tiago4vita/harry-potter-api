package com.example.harrypotter.api

import android.R
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HarryPotterAPI {

    @GET("character/{personagemId}")
    suspend fun getPersonagemInfo(
        @Path("personagemId") personagemId: String
    ): Response<PersonagemInfo>

    @GET("characters/staff")
    suspend fun getAllProfessores(): Response<List<ProfessorInfo>>

    @GET("characters/students")
    suspend fun getAllStudents(): Response<List<EstudanteInfo>>

    @GET("characters/house/{houseName}")
    suspend fun getCharactersByHouse(
        @Path("houseName") houseName: String
    ): Response<List<PersonagemInfo>>

    @GET("spells")
    suspend fun getAllSpells(): Response<List<SpellInfo>>
}

data class PersonagemInfo(
    val id: String,
    val name: String,
    val species: String,
    val house: String,
    val image: String
)

data class EstudanteInfo(
    val id: String,
    val name: String,
    @SerializedName("alternate_names")
    val alternateNames: List<String> = emptyList(),
    val species: String,
    val gender: String,
    val house: String,
    val dateOfBirth: String,
    val yearOfBirth: String,
    val wizard: Boolean,
    val ancestry: String,
    val eyeColour: String,
    val hairColour: String,
    val wand: WandInfo,
    val patronus: String,
    val hogwartsStudent: Boolean,
    val hogwartsStaff: Boolean,
    val actor: String,
    @SerializedName("alternate_actors")
    val alternateActors: List<String> = emptyList(),
)

data class ProfessorInfo(
    val id: String,
    val name: String,
    @SerializedName("alternate_names")
    val alternateNames: List<String> = emptyList(),
    val species: String,
    val gender: String,
    val house: String,
    val dateOfBirth: String,
    val yearOfBirth: Int,
    val wizard: Boolean,
    val ancestry: String,
    val eyeColour: String,
    val hairColour: String,
    val wand: WandInfo,
    val patronus: String,
    val hogwartsStudent: Boolean,
    val hogwartsStaff: Boolean,
    val actor: String,
    @SerializedName("alternate_actors")
    val alternateActors: List<String> = emptyList(),
    val alive: Boolean,
    val image: String
)

data class WandInfo(
    val wood: String,
    val core: String,
    val length: Double?
)

data class SpellInfo(
    val id: String,
    val name: String,
    val description: String
)