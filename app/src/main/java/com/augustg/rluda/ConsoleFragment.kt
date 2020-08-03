package com.augustg.rluda

import android.os.Bundle
import android.os.SystemClock
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.augustg.rluda.databinding.FragmentConsoleBinding
import com.augustg.rluda.library.logs.FormattedLog
import com.augustg.rluda.library.LoggerDude
import com.augustg.rluda.util.toggleVisibility

class ConsoleFragment : Fragment() {

    private lateinit var binding: FragmentConsoleBinding

    var open = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConsoleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.consoleText.apply {
            movementMethod = ScrollingMovementMethod()
            setOnClickListener {
                LoggerDude.log("Clicked on Log Console")
            }
        }

        binding.consoleMenu.setOnClickListener {
            toggleConsoleVisibility()
        }

        binding.clearAllButton.setOnClickListener {
            LoggerDude.clear()
            SystemClock.sleep(50)
            LoggerDude.log("Cleared Logs")
        }

        LoggerDude.live().observe(viewLifecycleOwner, Observer { logs ->
            formatAndDisplay(logs)
        })
    }

    private fun formatAndDisplay(logs: List<FormattedLog>) {
        binding.consoleText.apply {
            text = ""
            append(logs.joinToString("\n"))
        }
    }

    private fun toggleConsoleVisibility() {
        if (open) {
            open = false
            LoggerDude.log("Closed Log Console")
        } else {
            open = true
            LoggerDude.log("Opened Log Console")
        }
        binding.consoleText.toggleVisibility()
    }
}
