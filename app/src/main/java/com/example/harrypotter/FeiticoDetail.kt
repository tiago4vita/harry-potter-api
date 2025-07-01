package com.example.harrypotter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harypotterapi.R

class FeiticoDetail : AppCompatActivity() {
    private lateinit var textViewNomeFeitico: TextView
    private lateinit var textViewDescricaoFeitico: TextView
    private lateinit var btnVoltar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feitico_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nome = intent.getStringExtra("spellName") ?: "Unknown spell"
        val descricao = intent.getStringExtra("spellDescription") ?: "No description available"
        textViewNomeFeitico = findViewById<TextView>(R.id.textViewNomeFeitico)
        textViewDescricaoFeitico = findViewById<TextView>(R.id.textViewDescricaoFeitico)
        textViewNomeFeitico.text = nome;
        textViewDescricaoFeitico.text = descricao;

        btnVoltar = findViewById<Button>(R.id.btnVoltarListaFeiticos)
        btnVoltar.setOnClickListener {
            finish()
        }
    }
}