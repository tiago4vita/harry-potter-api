package com.example.harrypotter.model
/**
 * Represents a character (professor ou aluno) in the application.
 *
 * @property name The primary name of the character.
 * @property species The species of the character.
 * @property house The house affiliation of the character (if applicable).
 * @property image The URL string for the character's image.
 * @property alternateNames A list of alternative names for the character. Defaults to an empty list.
 *
 */
data class Personagem(
    val id: String,
    val name: String,
    val species: String,
    val house: String,
    val image: String, // URL for the character's image
    val alternateNames: List<String> = emptyList() // Renamed and default value
)