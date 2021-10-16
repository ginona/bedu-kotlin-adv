package com.gino.projectbedu.fragments

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.gino.projectbedu.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import okhttp3.*
import org.json.JSONObject

/**
 * Fragment de inicio
 */
class LoginFragment : Fragment() {
    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var bottomNavigationView: BottomNavigationView
    private var sharedPreferences: SharedPreferences? = null
    private val baseUrl = "https://reqres.in/api/users/"

    private val emailRequired = "Formato de E-mail incorrecto."
    private val passwordRequired = "Password is required."
    private val userNotFound = "Usuario no encontrado."
    private val snackbarText = "Entendido"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity = requireActivity()
        bottomNavigationView = activity.findViewById(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.GONE

        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val textRegister = view.findViewById<TextView>(R.id.textRegister)
        emailText = view.findViewById(R.id.emailText)
        passwordText = view.findViewById(R.id.passwordText)

        /**
         * Si se cumplen las condiciones especificadas, se podría iniciar sesión
         * y navegar directamente hacia la lista de productos.
         */
        btnLogin.setOnClickListener {
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText.text).matches()) emailText.error = emailRequired
            else if (TextUtils.isEmpty(passwordText.text)) passwordText.error = passwordRequired
            else {
                Thread{
                    checkUserEmail(view)
                }.start()
            }
        }
        /**
         * En caso de querer registrarse, al hacer clic en el text
         * te redigirá a la pantalla de Registro
         */
        textRegister.setOnClickListener {
                findNavController().navigate(R.id.action_login_dest_to_register_dest, null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater!!)
        menu.clear()
    }

    private fun checkUserEmail(view: View) {
        var check = false
        for(userNumber in 1..12){
            val okHttpClient = OkHttpClient()
            val url = "$baseUrl$userNumber"
            val request = Request.Builder()
                .url(url)
                .build()

            try {
                val response = okHttpClient.newCall(request).execute()
                val body = response.body?.toString()

                val json = JSONObject(body)
                val dataJSON = JSONObject(json.getString("data"))
                val email = dataJSON.getString("email")
                if(emailText.text.toString() == email){
                    activity?.runOnUiThread {
                        sharedPreferences?.edit()
                            ?.putString("USER_EMAIL", emailText.text.toString())
                            ?.putString("USER_PASSWORD", passwordText.text.toString())
                            ?.putString("USER_IMAGE", dataJSON.getString("avatar"))
                            ?.putString("USER_FIRST_NAME", dataJSON.getString("first_name"))
                            ?.apply()

                        //loginSuccessfully()
                        findNavController().navigate(R.id.action_login_dest_to_shop_dest, null)
                    }
                    check = true
                    break
                }
            }catch (e: Exception) {
                e.printStackTrace()
            }
        }
        if(!check){
            activity?.runOnUiThread {
                //loginProgressBar.visibility = View.INVISIBLE
                //loginButton.isEnabled = true

            }

            Snackbar.make(
                view,
                userNotFound,
                Snackbar.LENGTH_SHORT
            )
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                .setAction(snackbarText) {}.show()}
    }
}