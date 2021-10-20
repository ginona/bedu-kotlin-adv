package com.gino.projectbedu.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gino.projectbedu.R
import com.gino.projectbedu.domain.Element

/**
 * Adaptador empleado para mostrar los elementos en la lista.
 */
class ElementsAdapter(
    private val elements: List<Element>,
    private val click_listener: (String) -> Unit
    ): RecyclerView.Adapter<ElementsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.fragment_profile_elements, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentElement: Element = elements[position]

        holder.bind(currentElement)

        holder.itemView.setOnClickListener{
            click_listener(currentElement.name)
        }
    }

    override fun getItemCount(): Int {
        return elements.size
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val elementName = view.findViewById(R.id.element_name) as TextView
        val elementImage = view.findViewById(R.id.element_image) as ImageView

        /**
         * Enlazamos algunas variables del producto que obtenemos desde el JSON
         * con los declarados propios del objeto.
         */
        fun bind(element: Element){
            elementName.text = element.name
            elementImage.setImageResource(element.iconId)
        }
    }

}