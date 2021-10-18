package com.gino.projectbedu.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.gino.projectbedu.R
import com.gino.projectbedu.adapters.ElementsAdapter
import com.gino.projectbedu.domain.Element
import com.google.android.material.imageview.ShapeableImageView

/**
 * Fragment vacío a modo ejemplo momentaneamente.
 */
class ProfileFragment : Fragment() {
    private lateinit var recycler: RecyclerView
    private lateinit var userName: TextView
    private lateinit var userPhoto: ShapeableImageView
    private lateinit var userEmail: TextView
    private var sharedPreferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler = view.findViewById(R.id.elementRecyclerView)
        userName = view.findViewById(R.id.user_name)
        userPhoto = view.findViewById(R.id.profile_image)
        userEmail = view.findViewById(R.id.user_email)
        val clickListener: () -> Unit = {}

        recycler.adapter = ElementsAdapter(getProfileElements(), clickListener)
        recycler.layoutManager = LinearLayoutManager(activity)

        sharedPreferences =
            this.activity?.getSharedPreferences("com.gino.projectbedu", Context.MODE_PRIVATE)

        profileData()
    }

    private fun profileData(){
        userName.setText(sharedPreferences?.getString("USER_NAME","Eve"))
        userEmail.setText(sharedPreferences?.getString("USER_EMAIL","eve.holt@reqres.in"))
        userPhoto.load(sharedPreferences?.getString("USER_PHOTO","https://reqres.in/img/faces/4-image.jpg"))
    }

    private fun getProfileElements(): List<Element>{
        return listOf(
            Element("Mis direcciones",R.drawable.location),
            Element("Método de pago",R.drawable.card),
            Element("Pedidos",R.drawable.restore),
            Element("Notificaciones",R.drawable.notifications_active),
            Element("Cambiar contraseña",R.drawable.lock)
        )
    }
}