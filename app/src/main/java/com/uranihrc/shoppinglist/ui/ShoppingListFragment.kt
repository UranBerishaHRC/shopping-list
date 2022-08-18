package com.uranihrc.shoppinglist.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.uranihrc.shoppinglist.R
import com.uranihrc.shoppinglist.ui.adapters.ShoppingItemAdapter
import com.uranihrc.shoppinglist.utils.formatPrice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_shopping.*
import javax.inject.Inject


@AndroidEntryPoint
class ShoppingListFragment @Inject constructor(
    val shoppingItemAdapter: ShoppingItemAdapter,
    var shoppingViewModel: ShoppingViewModel? = null
) : Fragment(R.layout.fragment_shopping) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shoppingViewModel = shoppingViewModel ?: ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)
        subscribeToObservers()
        setupRecyclerView()

        fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(
                ShoppingListFragmentDirections.actionShoppingListFragmentToAddShoppingFragment()
            )
        }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0, LEFT or RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ) = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = shoppingItemAdapter.shoppingItems[pos]
            shoppingViewModel?.deleteShoppingItem(item)
            Snackbar.make(requireView(), "Successfully deleted item", Snackbar.LENGTH_LONG).apply {
                setAction("Undo") {
                    shoppingViewModel?.insertShoppingItemInDB(item)
                }
                show()
            }
        }
    }

    private fun subscribeToObservers() {
        shoppingViewModel?.getShoppingItems?.observe(viewLifecycleOwner, Observer {
            shoppingItemAdapter.shoppingItems = it
        })
        shoppingViewModel?.totalPrice?.observe(viewLifecycleOwner, Observer {
            val price = it ?: 0F
            val priceText = "Total Price: ${formatPrice(price)}€"
            tvShoppingItemPrice.text = priceText
        })
    }

    private fun setupRecyclerView() {
        rvShoppingItems.apply {
            adapter = shoppingItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }
}