package com.gino.projectbedu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gino.projectbedu.R
import com.gino.projectbedu.adapters.ElementsAdapter

/**
 * Fragment vacío a modo ejemplo momentaneamente.
 */
class ProfileFragment : Fragment() {
    private lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.elementRecyclerView)
        val clickListener: () -> Unit = {}

        recycler.adapter = ElementsAdapter(getProfileElements(), clickListener)
        recycler.layoutManager = LinearLayoutManager(activity)
    }

    private fun getProfileElements(): List<Element>{
        return listOf(
            Element("Mis direcciones",R.drawable.location),
            Element("Método de pago",R.drawable.card),
            Element("Pedidos",R.drawable.restore),
            Element("Notificaciones",R.drawable.notifications_active),
            Element("Cambiar contraseña",R.drawable.lock))
    }
}