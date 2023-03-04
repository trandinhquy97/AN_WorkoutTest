package com.example.annewandroid2023.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.annewandroid2023.R
import com.example.annewandroid2023.databinding.FragmentNotificationDialogBinding

class NotificationDialog : DialogFragment() {
    private var _binding: FragmentNotificationDialogBinding? = null
    val binding get() = _binding!!

    override fun getTheme() = R.style.RoundedCornersDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationDialogBinding.inflate(inflater, container, false)

        return binding.root
    }
}