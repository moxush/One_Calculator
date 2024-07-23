package com.example.calculator

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etFirstNumber: EditText
    private lateinit var etSecondNumber: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnSubtract: Button
    private lateinit var btnMultiply: Button
    private lateinit var btnDivide: Button
    private lateinit var tvResult: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etFirstNumber = findViewById(R.id.etFirstNumber)
        etSecondNumber = findViewById(R.id.etSecondNumber)
        btnAdd = findViewById(R.id.btnAdd)
        btnSubtract = findViewById(R.id.btnSubtract)
        btnMultiply = findViewById(R.id.btnMultiply)
        btnDivide = findViewById(R.id.btnDivide)
        tvResult = findViewById(R.id.tvResult)

        btnAdd.setOnClickListener { performOperation(Operation.ADD) }
        btnSubtract.setOnClickListener { performOperation(Operation.SUBTRACT) }
        btnMultiply.setOnClickListener { performOperation(Operation.MULTIPLY) }
        btnDivide.setOnClickListener { performOperation(Operation.DIVIDE) }

        // Add ResultFragment dynamically
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragmentContainer, ResultFragment())
        fragmentTransaction.commit()
    }

    private fun performOperation(operation: Operation) {
        val num1 = etFirstNumber.text.toString().toDoubleOrNull()
        val num2 = etSecondNumber.text.toString().toDoubleOrNull()

        if (num1 == null || num2 == null) {
            tvResult.text = "Please enter valid numbers"
            return
        }

        val result = when (operation) {
            Operation.ADD -> num1 + num2
            Operation.SUBTRACT -> num1 - num2
            Operation.MULTIPLY -> num1 * num2
            Operation.DIVIDE -> if (num2 != 0.0) num1 / num2 else "Cannot divide by zero"
        }

        tvResult.text = result.toString()

        // Update the result in the fragment
        val resultFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as ResultFragment
        resultFragment.updateResult(result.toString())
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                tvResult.text = "Calculator App v1.0"
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

enum class Operation {
    ADD, SUBTRACT, MULTIPLY, DIVIDE
}
