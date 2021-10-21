package com.gino.projectbedu.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import coil.api.load
import com.gino.projectbedu.R

class ProductCartFragment : Fragment() {
    private lateinit var cart_product_title: TextView
    private lateinit var cart_product_price: TextView
    private lateinit var cart_product_image: ImageView
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE)

        cart_product_title = view.findViewById(R.id.cart_product_title)
        cart_product_price = view.findViewById(R.id.cart_product_price)
        cart_product_image = view.findViewById(R.id.cart_product_image)

        cart_product_title.text = sharedPreferences?.getString("PROD_TITLE","Product")
        cart_product_price.text = sharedPreferences?.getString("PROD_PRICE","Price")
    }
}