package com.osenov.foodtest.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.osenov.foodtest.databinding.FragmentShoppingMenuBinding


class ShoppingMenuFragment : Fragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentShoppingMenuBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageQRCode.setOnClickListener{
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_LONG).show()
        }

        binding.tabLayoutCategory.addTab(binding.tabLayoutCategory.newTab().setText("Пицца"))
        binding.tabLayoutCategory.addTab(binding.tabLayoutCategory.newTab().setText("Комбо"))
        binding.tabLayoutCategory.addTab(binding.tabLayoutCategory.newTab().setText("Закуски"))
    }


}