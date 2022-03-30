package com.osenov.foodtest.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.osenov.foodtest.R
import com.osenov.foodtest.databinding.FragmentShoppingMenuBinding
import com.osenov.foodtest.domain.entity.Advertising


class ShoppingMenuFragment : Fragment() {

    private val viewModel: ShoppingMenuViewModel by viewModels()

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

    private val advertisingAdapter = AdvertisingAdapter {
        Toast.makeText(requireContext(), "click in ${it.imageUrl}", Toast.LENGTH_LONG).show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showAdvertising(viewModel.getAdvertising())

        binding.imageQRCode.setOnClickListener {
            Toast.makeText(requireContext(), "Click", Toast.LENGTH_LONG).show()
        }

        binding.tabLayoutCategory.addTab(binding.tabLayoutCategory.newTab().setText("Пицца"))
        binding.tabLayoutCategory.addTab(binding.tabLayoutCategory.newTab().setText("Комбо"))
        binding.tabLayoutCategory.addTab(binding.tabLayoutCategory.newTab().setText("Закуски"))
    }

    private fun showAdvertising(advertising: List<Advertising>) {
        binding.recyclerViewAdvertising.adapter = advertisingAdapter
        binding.recyclerViewAdvertising.addItemDecoration(
            AdvertisingItemDecoration(
                resources.getDimensionPixelSize(
                    R.dimen.advertising_recycler_offset
                )
            )
        )
        advertisingAdapter.setData(advertising)
    }


}