package com.example.harrypotter

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnListarPersonagem = findViewById<Button>(R.id.btnListarPersonagem)
        btnListarPersonagem.setOnClickListener {
                val intent = Intent(this, PersonagemActivity::class.java)
                startActivity(intent)
        }

        val btnListarProfessor = findViewById<Button>(R.id.btnListarProfessor)
        btnListarProfessor.setOnClickListener {
                val intent = Intent(this, ProfessorActivity::class.java)
                startActivity(intent)
        }

        val btnListarEstudantes = findViewById<Button>(R.id.btnListarEstudantes)
        btnListarEstudantes.setOnClickListener {
                val intent = Intent(this, EstudantesActivity::class.java)
                startActivity(intent)
        }

        val btnVerFeiticos = findViewById<Button>(R.id.btnVerFeiticos)
        btnVerFeiticos.setOnClickListener {
                val intent = Intent(this, FeiticosActivity::class.java)
                startActivity(intent)
        }

        val btnSair = findViewById<Button>(R.id.btnSair)
        btnSair.setOnClickListener {
                finishAffinity()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}