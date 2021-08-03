package com.example.quizapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.example.quizapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        val button = findViewById<Button>(R.id.btn_start)
        val textName = findViewById<AppCompatEditText>(R.id.et_name)

        button.setOnClickListener {

            if (textName.text.toString().isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME,binding.etName.text.toString())
                startActivity(intent)
                finish()
            }

        }
    }
}