package com.gino.projectbedu

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment() {
    private val requiredMessage : String = "Ingresa un elemento válido."

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val txtName = view.findViewById<EditText>(R.id.fullName)
        val txtEmail = view.findViewById<EditText>(R.id.emailField)
        val txtTelephone = view.findViewById<EditText>(R.id.telephone)
        val txtPassword = view.findViewById<EditText>(R.id.password)

        btnRegister.setOnClickListener {
            if (TextUtils.isEmpty(txtName.text)) txtName.error = requiredMessage
            else if (!Patterns.EMAIL_ADDRESS.matcher(txtEmail.text).matches()) txtEmail.error = requiredMessage
            else if (TextUtils.isEmpty(txtTelephone.text)) txtTelephone.error = requiredMessage
            else if (TextUtils.isEmpty(txtPassword.text)) txtPassword.error = requiredMessage
            else{
                btnRegister?.setOnClickListener {
                    findNavController().navigate(R.id.login_dest, null)
                }
            }
        }
    }
}