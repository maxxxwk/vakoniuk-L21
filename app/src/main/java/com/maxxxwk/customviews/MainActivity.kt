package com.maxxxwk.customviews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.maxxxwk.customviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListeners()
    }

    private fun setupListeners() {
        with(binding) {
            btnIncreasePower.setOnClickListener {
                fanView.increasePower()
                fanControlView.increasePower()
            }
            btnDecreasePower.setOnClickListener {
                fanView.decreasePower()
                fanControlView.decreasePower()
            }
            btnStartSecondTaskActivity.setOnClickListener {
                SecondActivity.start(this@MainActivity)
            }
        }
    }
}