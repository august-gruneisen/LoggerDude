package com.augustg.rluda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.augustg.rluda.databinding.ActivityMainBinding
import com.augustg.rluda.library.LoggerDude

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize LoggerDude
        LoggerDude.initialize(
            context = applicationContext,
            endpoint = "https://tomcat.creationgrowth.com/logger-dude/log/"
        )
    }
}