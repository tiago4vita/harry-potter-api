package com.example.harrypotter

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.harrypotter.api.SpellInfo
import com.example.harrypotter.client.RetrofitClient
import com.example.harypotterapi.R
import kotlinx.coroutines.launch

class FeiticosActivity : AppCompatActivity() {
    private lateinit var listViewFeiticos: ListView
    private var feiticos = listOf<SpellInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feiticos)

        listViewFeiticos = findViewById<ListView>(R.id.listViewFeiticos)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        carregarFeiticos();

        listViewFeiticos.setOnItemClickListener { parent, view, position, id ->
            val selectedSpell = feiticos[position]
            val intent = Intent(this, FeiticoDetail::class.java).apply {
                putExtra("spellId", selectedSpell.id)
                putExtra("spellName", selectedSpell.name)
                putExtra("spellDescription", selectedSpell.description)
            }
            startActivity(intent)
        }
    }

    private fun carregarFeiticos() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.getAllSpells()
                if (response.isSuccessful) {
                    feiticos = response.body() ?: emptyList()
                    val adapter = ArrayAdapter(
                        this@FeiticosActivity,
                        android.R.layout.simple_list_item_1,
                        feiticos.map { it.name }
                    )
                    listViewFeiticos.adapter = adapter
                } else {
                    Toast.makeText(this@FeiticosActivity, "Erro ao carregar feitiços", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@FeiticosActivity, "Erro: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        carregarFeiticos()
    }
}