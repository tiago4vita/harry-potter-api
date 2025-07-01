package com.example.harrypotter

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.harrypotter.api.ProfessorInfo
import com.example.harrypotter.client.RetrofitClient
import com.example.harypotterapi.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfessorActivity : AppCompatActivity() {
    private lateinit var btnVoltar: Button
    private var professores = listOf<ProfessorInfo>()
    private lateinit var listViewProfessores: ListView
    private lateinit var loader: TextView
    private lateinit var searchViewProfessores: SearchView
    private lateinit var cardProfessor: CardView
    private lateinit var imagemProfessor: ImageView
    private lateinit var nomeProfessor: TextView
    private lateinit var nomesAlternativos: TextView
    private lateinit var especieProfessor: TextView
    private lateinit var casaProfessor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_professor)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }
    }

    private fun carregarProfessores() {
        loader.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getAllProfessores()
                if (response.isSuccessful) {
                    professores = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(
                        this@ProfessorActivity,
                        android.R.layout.simple_list_item_1,
                        professores.map { it.name }
                    )
                    listViewProfessores.adapter = adapter
                    loader.visibility = View.GONE
                } else {
                    loader.text = "Error fetching professors from the API"
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ProfessorActivity, "Erro fetching professors", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProfessorActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun mostrarInfo(professor: ProfessorInfo) {
        cardProfessor.visibility = View.VISIBLE;
        nomeProfessor.visibility = View.VISIBLE;
        imagemProfessor.visibility = View.VISIBLE;

        val houseColour = when (professor.house) {
            "Gryffindor" -> ContextCompat.getColor(this, R.color.gryffindor)
            "Ravenclaw" -> ContextCompat.getColor(this, R.color.ravenclaw)
            "Hufflepuff" -> ContextCompat.getColor(this, R.color.hufflepuff)
            "Slytherin" -> ContextCompat.getColor(this, R.color.slytherin)
            else -> ContextCompat.getColor(this, R.color.background)
        }

        val badgeBackground = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 24f
            setColor(houseColour)
        }

        casaProfessor.setBackground(badgeBackground);
        if (professor.house == "Hufflepuff") {
            casaProfessor.setTextColor(getColor(R.color.colorPrimary))
        } else {
            casaProfessor.setTextColor(ContextCompat.getColor(this, R.color.background))
        }

        nomeProfessor.text = professor.name;
        if (professor.alternateNames.isNotEmpty()) {
            nomesAlternativos.text = professor.alternateNames.joinToString(", ")
            nomesAlternativos.visibility = View.VISIBLE;
        } else nomesAlternativos.visibility = View.GONE
        especieProfessor.text = professor.species
        casaProfessor.text = professor.house

        Glide.with(this)
            .load(professor.image)
            .error(R.drawable.baseline_do_not_disturb_24)
            .circleCrop()
            .into(imagemProfessor)

    }

    override fun onResume() {
        super.onResume()
        carregarProfessores()
    }
}