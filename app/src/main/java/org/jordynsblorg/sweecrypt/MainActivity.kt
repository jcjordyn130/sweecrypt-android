package org.jordynsblorg.sweecrypt

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

const val TAG = "sweecrypt"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.doneButton).setOnClickListener {
            onDone()
        }
    }

    fun onDone() {
        // Get items
        val inputEditText = findViewById<EditText>(R.id.inputEditText)
        val shiftEditText = findViewById<EditText>(R.id.shiftEditText)
        val operationGroup = findViewById<RadioGroup>(R.id.operationGroup)

        // Get selected operation button
        val selectedOperationId = operationGroup.checkedRadioButtonId
        if (selectedOperationId == -1) {
            Log.e(TAG, "selectedOperationId == -1 during onDone()")
            return
        }

        // Get shift
        // Defaults to 0
        val shift = shiftEditText.text.toString().toIntOrNull() ?: 0

        // Get input text
        val inputText = inputEditText.text.toString()

        // Run operation
        when (selectedOperationId) {
            R.id.encryptButton -> {
                inputEditText.setText(libsc.encrypt(inputText, shift))
            }

            R.id.decryptButton -> {
                inputEditText.setText(libsc.decrypt(inputText, shift))
            }

            else -> {
                Log.e(TAG, "invalid selectedOperationId: $selectedOperationId")
                return
            }
        }
    }
}