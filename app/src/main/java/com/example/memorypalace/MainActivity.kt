package com.example.memorypalace

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private lateinit var detailView: GridLayout
    private lateinit var backButton: Button
    private lateinit var topLeftText: EditText
    private lateinit var topRightText: EditText
    private lateinit var bottomLeftText: EditText
    private lateinit var bottomRightText: EditText
    private lateinit var centerText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicjalizacja widoków
        gridLayout = findViewById(R.id.gridLayout)
        detailView = findViewById(R.id.detailView)
        backButton = findViewById(R.id.backButton)
        topLeftText = findViewById(R.id.topLeftText)
        topRightText = findViewById(R.id.topRightText)
        bottomLeftText = findViewById(R.id.bottomLeftText)
        bottomRightText = findViewById(R.id.bottomRightText)
        centerText = findViewById(R.id.centerText)

        populateGrid()
        setupBackButton()
    }

    private fun populateGrid() {
        // Dynamiczne generowanie siatki 50x50
        for (i in 0 until 50 * 50) {
            val square = TextView(this).apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 40
                    height = 40
                    marginEnd = 1
                    bottomMargin = 1
                }
                text = ""
                setBackgroundColor(0xFFCCCCCC.toInt())
                tag = i
                setOnClickListener {
                    showDetailView(tag.toString())
                }
            }
            gridLayout.addView(square)
        }
    }

    private fun showDetailView(tag: String) {
        gridLayout.visibility = View.GONE
        detailView.visibility = View.VISIBLE

        // Możesz zapisywać i odczytywać dane dla każdego kwadratu tutaj.
        // Na przykład: przypisz `tag` do pól tekstowych, jeśli potrzebujesz unikalnych danych.
    }

    private fun setupBackButton() {
        backButton.setOnClickListener {
            detailView.visibility = View.GONE
            gridLayout.visibility = View.VISIBLE
        }
    }
}
