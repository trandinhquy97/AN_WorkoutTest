package com.example.annewandroid2023

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.*
import com.example.annewandroid2023.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        // Lay out app in full screen
        WindowCompat.setDecorFitsSystemWindows(window, false)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, windowInsets ->
            val systemBarInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Avoid overlap system bars insets
            view.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                leftMargin = systemBarInsets.left
                bottomMargin = systemBarInsets.bottom
                rightMargin = systemBarInsets.right
            }

            // Avoid overlap system gesture insets
            val gestureInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemGestures())
            view.updatePadding(
                0,
                gestureInsets.top,
                0,
                0
            )
            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            WindowInsetsCompat.CONSUMED
        }

        setContentView(binding.root)
    }
}