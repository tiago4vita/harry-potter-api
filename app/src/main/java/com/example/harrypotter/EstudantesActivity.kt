package com.example.harrypotter

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.harrypotter.api.HarryPotterAPI
import com.example.harypotterapi.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.harrypotter.client.RetrofitClient

class EstudantesActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var getCharactersByHouse: HarryPotterAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_estudantes)

        tvResult = findViewById(R.id.tvResult)

        val btnVoltar = findViewById<Button>(R.id.btnVoltar)
        btnVoltar.setOnClickListener {
            finish()
        }

        //getCharactersByHouse = retrofit.create(HarryPotterAPI::class.java)
    }

//    fun getCharactersByHouse(houseName: String) {
//        lifecycleScope.launch {
//            try {
//                val response = withContext(Dispatchers.IO) {
//                    getCharactersByHouse.getCharactersByHouse(houseName)
//                }
//            }
//        }
//    }
}