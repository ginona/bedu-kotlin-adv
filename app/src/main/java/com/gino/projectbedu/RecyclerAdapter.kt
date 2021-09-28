package com.gino.projectbedu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load

/**
 * Adaptador empleado para mostrar los elementos en la lista.
 */
class RecyclerAdapter(
    private val context:Context,
    private val products: MutableList<Product>,
    private val clickListener: (Product) -> Unit): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products.get(position)
        holder.bind(product, context)
        holder.view.setOnClickListener{clickListener(product)}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_product, parent, false))
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val productName = view.findViewById(R.id.tvProduct) as TextView
        val rating = view.findViewById<RatingBar>(R.id.rbRate)
        val price = view.findViewById(R.id.tvPrice) as TextView
        val image = view.findViewById(R.id.imgProduct) as ImageView

        /**
         * Enlazamos algunas variables del producto que obtenemos desde el JSON
         * con los declarados propios del objeto.
         */
        fun bind(product: Product, context: Context){
            productName.text = product.title
            rating.rating = product.rating
            price.text = product.price
            image.load(product.image)
        }
    }

}