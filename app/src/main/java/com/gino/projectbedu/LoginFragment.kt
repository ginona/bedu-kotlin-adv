package com.gino.projectbedu

import android.app.Activity
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
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    //private lateinit var bottomNavigationView: BottomNavigationView

    private val emailRequired = "Email is required."
    private val passwordRequired = "Password is required."

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //bottomNavigationView = view.findViewById(R.id.bottom_navigation)
        //bottomNavigationView.visibility = View.GONE
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val textRegister = view.findViewById<TextView>(R.id.textRegister)
        emailText = view.findViewById(R.id.emailText)
        passwordText = view.findViewById(R.id.passwordText)


        btnLogin.setOnClickListener {
            if (TextUtils.isEmpty(emailText.text))
                emailText.error = emailRequired
            if (TextUtils.isEmpty(passwordText.text))
                passwordText.error = passwordRequired
            else{
                btnLogin?.setOnClickListener {
                    findNavController().navigate(R.id.shop_dest, null)
                }
            }
        }

        textRegister.setOnClickListener {
            textRegister?.setOnClickListener {
                findNavController().navigate(R.id.register_dest, null)
            }
        }
    }
}