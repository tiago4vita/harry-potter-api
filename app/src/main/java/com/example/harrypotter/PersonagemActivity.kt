package com.example.harrypotter

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.harrypotter.api.PersonagemInfo
import com.example.harrypotter.client.RetrofitClient
import com.example.harypotterapi.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PersonagemActivity : AppCompatActivity() {
    
    private lateinit var editTextPersonagemId: EditText
    private lateinit var btnBuscar: Button
    private lateinit var tvResultado: TextView
    private lateinit var imagePersonagem: ImageView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_personagem)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextPersonagemId = findViewById(R.id.editTextPersonagemId)
        btnBuscar = findViewById(R.id.btnBuscar)
        tvResultado = findViewById(R.id.tvResultado)
        imagePersonagem = findViewById(R.id.imagePersonagem)
        
        val btnVoltar = findViewById<Button>(R.id.btnVoltarHomeDoPersonagemPorId)
        btnVoltar.setOnClickListener {
            finish()
        }
        
        btnBuscar.setOnClickListener {
            val personagemId = editTextPersonagemId.text.toString().trim()
            if (personagemId.isNotEmpty()) {
                buscarPersonagem(personagemId)
            } else {
                Toast.makeText(this, "Type in a valid ID", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun buscarPersonagem(personagemId: String) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getPersonagemInfo(personagemId)
                if (response.isSuccessful) {
                    val personagens = response.body()
                    if (!personagens.isNullOrEmpty()) {
                        exibirResultado(personagens[0])
                    } else {
                        tvResultado.text = "Character not found"
                    }
                } else {
                    tvResultado.text = "Failed to fetch character: ${response.code()}"
                }
            } catch (e: Exception) {
                tvResultado.text = "Error: ${e.message}"
                Toast.makeText(this@PersonagemActivity, "Connection error", Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun exibirResultado(personagem: PersonagemInfo) {
        val resultado = """
            Name: ${personagem.name}
            Species: ${personagem.species}
            Hogwarts house: ${personagem.house}
        """.trimIndent()
        tvResultado.text = resultado

        // Load image using only standard Android APIs
        lifecycleScope.launch {
            if (personagem.image.isNotEmpty()) {
                try {
                    val bitmap = withContext(Dispatchers.IO) {
                        val url = java.net.URL(personagem.image)
                        val connection = url.openConnection()
                        connection.connect()
                        val input = connection.getInputStream()
                        BitmapFactory.decodeStream(input)
                    }
                    imagePersonagem.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    imagePersonagem.setImageResource(android.R.color.transparent)
                }
            } else {
                imagePersonagem.setImageResource(android.R.color.transparent)
            }
        }
    }
}