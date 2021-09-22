package com.gino.projectbedu

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    private val requiredMessage : String = "Required value."

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
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
            if (TextUtils.isEmpty(txtEmail.text)) txtEmail.error = requiredMessage
            if (TextUtils.isEmpty(txtTelephone.text)) txtTelephone.error = requiredMessage
            if (TextUtils.isEmpty(txtPassword.text)) txtPassword.error = requiredMessage
            else{
                btnRegister?.setOnClickListener {
                    findNavController().navigate(R.id.login_dest, null)
                }
            }
        }
    }
}