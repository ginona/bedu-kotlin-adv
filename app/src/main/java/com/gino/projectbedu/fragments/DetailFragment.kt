package com.gino.projectbedu.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import coil.api.load
import com.gino.projectbedu.domain.Product
import com.gino.projectbedu.R

/**
 * Fragmento utilizado para hacer visible el detalle
 * del producto que seleccionamos desde la lista completa.
 */
class DetailFragment : Fragment() {

    private lateinit var tvProduct: TextView
    private lateinit var rbRate: RatingBar
    private lateinit var tvPrice: TextView
    private lateinit var tvDescription: TextView
    private lateinit var imgProduct: ImageView
    private lateinit var btnAddToCart: Button
    private lateinit var productReceived: Product
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)
        /**
         * Tramos desde la vista los elementos propios de ella.
         */
        tvProduct = view.findViewById(R.id.tvProduct)
        rbRate = view.findViewById(R.id.rbRate)
        tvPrice = view.findViewById(R.id.tvPrice)
        tvDescription = view.findViewById(R.id.tvDescription)
        imgProduct = view.findViewById(R.id.imgProduct)
        btnAddToCart = view.findViewById(R.id.btnAddToCart)

        btnAddToCart.setOnClickListener {
            requireActivity().getSharedPreferences("shopping_cart", Context.MODE_PRIVATE).edit().apply {
                putString("PROD_PRICE", productReceived.price)
                putString("PROD_TITLE", productReceived.title)
                putString("PROD_IMAGE", productReceived.image)
            }.apply()
        }

        return view
    }

    fun showProduct(product: Product){
        /**
         * Asignamos a cada elemento de la vista
         * los datos correspondientes.
         */
        view?.visibility = View.VISIBLE
        tvProduct.text = product.title
        rbRate.rating = product.rating?.rate ?: 5f
        tvPrice.text = product.price
        tvDescription.text = product.description
        imgProduct.load(product.image)
        productReceived = product
    }

}