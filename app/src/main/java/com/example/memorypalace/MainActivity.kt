package com.example.memorypalace

import android.graphics.Color
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f

    private var currentRow = 0
    private var currentColumn = 0
    private lateinit var gridLayout: GridLayout

    private lateinit var detailsLayout: View
    private lateinit var detailFields: Map<String, EditText>
    private var selectedButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        detailsLayout = findViewById(R.id.detailsLayout)
        val backButton = findViewById<Button>(R.id.backButton)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val upButton = findViewById<Button>(R.id.upButton)
        val downButton = findViewById<Button>(R.id.downButton)
        val leftButton = findViewById<Button>(R.id.leftButton)
        val rightButton = findViewById<Button>(R.id.rightButton)

        detailFields = mapOf(
            "top" to findViewById(R.id.topText),
            "bottom" to findViewById(R.id.bottomText),
            "side1" to findViewById(R.id.side1Text),
            "side2" to findViewById(R.id.side2Text),
            "side3" to findViewById(R.id.side3Text),
            "side4" to findViewById(R.id.side4Text)
        )

        gridLayout.visibility = View.VISIBLE
        detailsLayout.visibility = View.GONE

        gridLayout.apply {
            columnCount = 50
            rowCount = 50
            for (i in 0 until 50 * 50) {
                val button = Button(this@MainActivity).apply {
                    text = ""
                    setBackgroundColor(Color.LTGRAY)
                    layoutParams = GridLayout.LayoutParams().apply {
                        width = 100
                        height = 100
                    }
                    setOnClickListener {
                        currentRow = i / 50
                        currentColumn = i % 50
                        showDetailsLayout(this)
                    }
                }
                addView(button)
            }
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

        upButton.setOnClickListener {
            if (currentRow > 0) {
                currentRow--
                navigateTo(currentRow, currentColumn)
            }
        }

        downButton.setOnClickListener {
            if (currentRow < 49) {
                currentRow++
                navigateTo(currentRow, currentColumn)
            }
        }

        leftButton.setOnClickListener {
            if (currentColumn > 0) {
                currentColumn--
                navigateTo(currentRow, currentColumn)
            }
        }

        rightButton.setOnClickListener {
            if (currentColumn < 49) {
                currentColumn++
                navigateTo(currentRow, currentColumn)
            }
        }

        scaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = scaleFactor.coerceIn(0.5f, 3.0f)
                gridLayout.scaleX = scaleFactor
                gridLayout.scaleY = scaleFactor
                return true
            }
        })

        gridLayout.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }
    }

    private fun showDetailsLayout(button: Button) {
        gridLayout.visibility = View.GONE
        detailsLayout.visibility = View.VISIBLE
        selectedButton = button

        detailFields.forEach { (position, field) ->
            field.setText(button.tag?.toString()?.split(",")?.getOrNull(when (position) {
                "top" -> 0
                "bottom" -> 1
                "side1" -> 2
                "side2" -> 3
                "side3" -> 4
                "side4" -> 5
                else -> -1
            }) ?: "")
        }
    }

    private fun navigateTo(row: Int, column: Int) {
        val index = row * 50 + column
        val button = gridLayout.getChildAt(index) as Button
        showDetailsLayout(button)
    }
}
