package com.augustg.rluda

import android.os.Bundle
import android.os.SystemClock
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.augustg.loggerdude.LoggerDude
import com.augustg.loggerdude.logs.FormattedLog
import com.augustg.rluda.databinding.FragmentConsoleBinding

class ConsoleFragment : Fragment() {

    private lateinit var binding: FragmentConsoleBinding

    private val consoleOpen by lazy { ObservableBoolean(false) }

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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.open = consoleOpen

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
        consoleOpen.set(
            if (consoleOpen.get()) {
                LoggerDude.log("Closing console")
                false
            } else {
                LoggerDude.log("Opening console")
                true
            }
        )
    }
}