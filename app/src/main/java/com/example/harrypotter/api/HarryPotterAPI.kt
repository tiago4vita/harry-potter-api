package com.example.harrypotter.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface HarryPotterAPI {

    @GET("character/{personagemId}")
    suspend fun getPersonagemInfo(
        @Path("personagemId") personagemId: String
    ): Response<Map<String, PersonagemInfo>>

    @GET("characters/staff")
    suspend fun getAllProfessores(): Response<Map<List<String>, ProfessorInfo>>

    @GET("characters/house/{houseName}")
    suspend fun getCharactersByHouse(
        @Path("houseName") houseName: String
    ): Response<Map<List<String>, PersonagemInfo>>

    @GET("spells")
    suspend fun getAllSpells(): Response<Map<List<String>, SpellInfo>>
}

data class PersonagemInfo(
    val id: String,
    val name: String,
    val species: String,
    val house: String,
    val image: String
)

data class ProfessorInfo(
    val id: String,
    val name: String,
    val species: String,
    val house: String,
    val image: String,
    val alternate_names: List<String> = emptyList()
)

data class SpellInfo(
    val name: String,
    val description: String
)