package com.augustg.rluda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.augustg.rluda.databinding.ActivityMainBinding
import com.augustg.rluda.library.StorageAccessor

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize LoggerDude
        StorageAccessor.initialize(applicationContext)
        StorageAccessor.clearLogs() // clear logs on app startup
    }
}