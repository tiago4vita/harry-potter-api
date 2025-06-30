package com.example.harrypotter

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.harrypotter.api.HarryPotterAPI
import com.example.harypotterapi.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.harrypotter.client.RetrofitClient

class EstudantesActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var btnConsultarCasa: Button
    private lateinit var rgHouses: RadioGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_estudantes)

        tvResult = findViewById(R.id.tvResult)
        btnConsultarCasa = findViewById(R.id.btnConsultarCasa)
        rgHouses = findViewById(R.id.rgHouses)

        btnConsultarCasa.setOnClickListener {
            val selectedId = rgHouses.checkedRadioButtonId
            val houseName = when (selectedId) {
                R.id.rbGryffindor -> "gryffindor"
                R.id.rbSlytherin -> "slytherin"
                R.id.rbHufflepuff -> "hufflepuff"
                R.id.rbRavenclaw -> "ravenclaw"
                else -> null
            }

            if (houseName != null) {
                carregarEstudantesPorCasa(houseName)
            } else {
                tvResult.text = "Por favor, selecione uma casa."
            }
        }

        carregarEstudantes()

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        //getCharactersByHouse = retrofit.create(HarryPotterAPI::class.java)
    }

    private fun carregarEstudantes() {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.api.getAllStudents()
                }

                if (response.isSuccessful) {
                    val studentsList = response.body()
                    if (!studentsList.isNullOrEmpty()) {
                        val namesBuilder = StringBuilder()
                        studentsList.forEach { student ->
                            namesBuilder.append(student.name).append("\n")
                        }
                        tvResult.text = namesBuilder.toString()
                    } else {
                        tvResult.text = "Nenhum estudante encontrado."
                    }
                } else {
                    tvResult.text = "Erro: ${response.code()} ${response.message()}"
                    Log.e("EstudantesActivity", "Erro na resposta: ${response.code()} ${response.message()}")
                }

            } catch (e: Exception) {
                Log.e("EstudantesActivity", "Erro ao carregar estudantes", e)
                tvResult.text = "Ocorreu um erro ao carregar estudantes."
            }
        }
    }

    private fun carregarEstudantesPorCasa(houseName: String) {
        lifecycleScope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.api.getCharactersByHouse(houseName)
                }

                if (response.isSuccessful) {
                    val personagens = response.body()
                    if (!personagens.isNullOrEmpty()) {
                        val builder = StringBuilder()
                        personagens.forEach { personagem ->
                            builder.append(personagem.name).append("\n")
                        }
                        tvResult.text = builder.toString()
                    } else {
                        tvResult.text = "Nenhum personagem encontrado na casa $houseName."
                    }
                } else {
                    tvResult.text = "Erro: ${response.code()} ${response.message()}"
                }

            } catch (e: Exception) {
                Log.e("EstudantesActivity", "Erro ao carregar personagens", e)
                tvResult.text = "Erro ao carregar personagens."
            }
        }
    }

}