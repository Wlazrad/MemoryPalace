package com.example.memorypalace

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridLayout = findViewById<GridLayout>(R.id.gridLayout)
        val detailsLayout = findViewById<View>(R.id.detailsLayout)
        val backButton = findViewById<Button>(R.id.backButton)
        val saveButton = findViewById<Button>(R.id.saveButton)

        val detailFields = mapOf(
            "top" to findViewById<EditText>(R.id.topText),
            "bottom" to findViewById<EditText>(R.id.bottomText),
            "left" to findViewById<EditText>(R.id.leftText),
            "right" to findViewById<EditText>(R.id.rightText),
            "center" to findViewById<EditText>(R.id.centerText)
        )

        var selectedButton: Button? = null

        gridLayout.visibility = View.VISIBLE
        detailsLayout.visibility = View.GONE

        for (i in 0 until 50 * 50) {
            val button = Button(this).apply {
                text = ""
                setBackgroundColor(Color.LTGRAY)
                setOnClickListener {
                    gridLayout.visibility = View.GONE
                    detailsLayout.visibility = View.VISIBLE
                    selectedButton = this

                    detailFields.forEach { (position, field) ->
                        field.setText(tag?.toString()?.split(",")?.getOrNull(when (position) {
                            "top" -> 0
                            "bottom" -> 1
                            "left" -> 2
                            "right" -> 3
                            "center" -> 4
                            else -> -1
                        }) ?: "")
                    }
                }
            }
            gridLayout.addView(button)
        }

        saveButton.setOnClickListener {
            selectedButton?.apply {
                val data = detailFields.values.joinToString(",") { it.text.toString() }
                tag = data

                val filledFields = detailFields.values.count { it.text.isNotEmpty() }
                when (filledFields) {
                    in 1..4 -> setBackgroundColor(Color.RED)
                    5 -> setBackgroundColor(Color.YELLOW)
                    else -> if (detailFields.values.all { it.text.isNotEmpty() }) {
                        setBackgroundColor(Color.GREEN)
                    }
                }

                Toast.makeText(this@MainActivity, "Saved!", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            gridLayout.visibility = View.VISIBLE
            detailsLayout.visibility = View.GONE
        }
    }
}
