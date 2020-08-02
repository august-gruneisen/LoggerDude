package com.augustg.rluda

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.augustg.rluda.databinding.FragmentConsoleBinding
import com.augustg.rluda.library.Log
import com.augustg.rluda.library.StorageAccessor
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
                StorageAccessor.storeLog("Clicked on Log Console")
            }
        }

        binding.consoleMenu.setOnClickListener {
            toggleConsoleVisibility()
        }

        binding.clearAllButton.setOnClickListener {
            StorageAccessor.clearLogs()
            StorageAccessor.storeLog("Cleared Logs")
        }

        StorageAccessor.observeLogs().observe(viewLifecycleOwner, Observer { logs ->
            formatAndDisplay(logs)
        })
    }

    private fun formatAndDisplay(logs: List<Log>) {
        binding.consoleText.text = ""
        binding.consoleText.append(
            logs.joinToString("\n") { log ->
                "${log.time.toString().substring(11)}: ${log.message}"
            }
        )
    }

    private fun toggleConsoleVisibility() {
        if (open) {
            open = false
            StorageAccessor.storeLog("Closed Log Console")
        } else {
            open = true
            StorageAccessor.storeLog("Opened Log Console")
        }
        binding.consoleText.toggleVisibility()
    }
}
