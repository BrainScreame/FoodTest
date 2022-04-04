package com.osenov.foodtest.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.osenov.foodtest.R
import com.osenov.foodtest.databinding.FragmentShoppingMenuBinding
import com.osenov.foodtest.domain.entity.Advertising
import com.osenov.foodtest.domain.entity.Product
import com.osenov.foodtest.ui.menu.advertising.AdvertisingAdapter
import com.osenov.foodtest.ui.menu.advertising.AdvertisingItemDecoration
import com.osenov.foodtest.ui.menu.product.ProductAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.osenov.foodtest.ui.menu.mediator.TabListMediator
import com.osenov.foodtest.ui.menu.product.ProductItemDecoration
import android.graphics.drawable.GradientDrawable
import android.widget.FrameLayout
import android.widget.TextView
import com.osenov.foodtest.util.Result
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DiffUtil
import com.osenov.foodtest.ui.menu.product.ProductDiffUtil
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ShoppingMenuFragment : Fragment() {

    private val viewModel: ShoppingMenuViewModel by viewModels()

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentShoppingMenuBinding.inflate(layoutInflater)
    }

    private val productAdapter = ProductAdapter {
        Toast.makeText(requireContext(), "click in ${it.name}", Toast.LENGTH_LONG).show()
    }

    private val advertisingAdapter = AdvertisingAdapter {
        Toast.makeText(requireContext(), "click in ${it.imageUrl}", Toast.LENGTH_LONG).show()
    }

    private val advertisingItemDecoration by lazy {
        AdvertisingItemDecoration(
            resources.getDimensionPixelSize(
                R.dimen.advertising_recycler_offset
            )
        )
    }

    private val productItemDecoration by lazy {
        val itemDecoration = ProductItemDecoration(requireContext())
        val drawable = GradientDrawable(
            GradientDrawable.Orientation.BOTTOM_TOP,
            resources.getIntArray(R.array.divider_color)
        )
        drawable.setSize(
            FrameLayout.LayoutParams.MATCH_PARENT,
            resources.getDimensionPixelSize(R.dimen.divider_item_decoration_height)
        )
        itemDecoration.setDrawable(drawable)
        itemDecoration
    }

    private val tabListMediator: TabListMediator by lazy(LazyThreadSafetyMode.NONE) {
        TabListMediator(binding.recyclerProducts, binding.tabLayoutCategory, binding.appBarProduct)
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

        viewModel.getProducts()
        setUI()
        addClickListeners()
        subscribeViewModel()
        showAdvertising(viewModel.getAdvertising())

    }

    private fun addClickListeners() {
        binding.imageQRCode.setOnClickListener {
            Toast.makeText(requireContext(), "QR CODE", Toast.LENGTH_LONG).show()
        }

        binding.textViewCity.setOnClickListener {
            Toast.makeText(requireContext(), "Select City", Toast.LENGTH_LONG).show()
        }
    }

    private fun subscribeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsUiState.collect { uiState ->
                    when (uiState) {
                        is ProductsUiState.Loading -> showStatusLoad()
                        is ProductsUiState.Success -> {
                            showMenu(uiState.products)
                        }
                        is ProductsUiState.Error -> {
                            showStatusError(uiState.message)
                        }
                    }
                }
            }
        }
    }

    private fun setUI() {
        binding.recyclerProducts.adapter = productAdapter
        binding.recyclerViewAdvertising.adapter = advertisingAdapter
    }

    private fun showStatusLoad() {
        with(binding) {
            coordinatorLayoutProduct.isVisible = false
            exceptionLayoutMenu.root.isVisible = false
            progressBarMenu.isVisible = true
        }
    }

    private fun showMenu(products: List<Product>) {
        with(binding) {
            progressBarMenu.isVisible = false
            exceptionLayoutMenu.root.isVisible = false
            coordinatorLayoutProduct.isVisible = true

            showProducts(products)
            if (binding.tabLayoutCategory.tabCount == 0) {
                showCategories(viewModel.getCategories(products))
            }
            tabListMediator.attach(viewModel.getCategoryPositions(products))
        }
    }

    private fun showStatusError(message: String?) {
        with(binding) {
            coordinatorLayoutProduct.isVisible = false
            progressBarMenu.isVisible = false
            exceptionLayoutMenu.root.isVisible = true

            exceptionLayoutMenu.errorMessage.text = message
            exceptionLayoutMenu.retry.setOnClickListener {
                viewModel.getProducts()
            }
        }
    }

    private fun showCategories(categories: List<String>) {
        for (i in categories.indices) {
            val textView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_tab_category, null) as TextView
            textView.text = categories[i]
            val tab = binding.tabLayoutCategory.newTab()
            tab.customView = textView
            binding.tabLayoutCategory.addTab(tab)

        }
    }

    private fun showProducts(data: List<Product>) {
        binding.recyclerProducts.addItemDecoration(productItemDecoration)
        val diffResult = DiffUtil.calculateDiff(ProductDiffUtil(productAdapter.getData(), data))
        productAdapter.setData(data)
        diffResult.dispatchUpdatesTo(productAdapter)
    }

    private fun showAdvertising(advertising: List<Advertising>) {
        binding.recyclerViewAdvertising.addItemDecoration(advertisingItemDecoration)
        advertisingAdapter.setData(advertising)
    }

    override fun onPause() {
        super.onPause()
        binding.recyclerProducts.removeItemDecoration(productItemDecoration)
        binding.recyclerViewAdvertising.removeItemDecoration(advertisingItemDecoration)
    }

    override fun onStop() {
        super.onStop()
        tabListMediator.detach()
    }


}