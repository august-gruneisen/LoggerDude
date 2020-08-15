package com.augustg.rluda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.augustg.loggerdude.LoggerDude
import com.augustg.rluda.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize LoggerDude
        LoggerDude.initialize(
            context = applicationContext,
            endpoint = "https://augustg.com/loggerdude/log/"
        )

        LoggerDude.log("MainActivity.onCreate")
    }
}