package com.gino.projectbedu.fragments

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
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
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

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

        sharedPreferences =
            this.activity?.getSharedPreferences("com.gino.projectbedu", Context.MODE_PRIVATE)
        /**
         * Si se cumplen las condiciones especificadas, se podría iniciar sesión
         * y navegar directamente hacia la lista de productos.
         */
        btnLogin.setOnClickListener {
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText.text).matches()) emailText.error = emailRequired
            else if (TextUtils.isEmpty(passwordText.text)) passwordText.error = passwordRequired
            else {
                Thread{
                    userValidation(view)
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

    private fun userValidation(view: View) {
            val okHttpClient = OkHttpClient()
            var found = false
            val request = Request.Builder()
                .url(baseUrl)
                .build()
                okHttpClient.newCall(request).enqueue(object: Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        Log.d("Error", e.toString())
                    }

                    override fun onResponse(call: Call, response: Response) {
                        try {
                                val json = JSONObject(response.body?.string())
                                Log.d("JsonArray", json.toString())
                                val dataJSON = JSONArray(json.getString("data"))
                            for (index in 0..dataJSON.length()){
                                val jsonObject = dataJSON.getJSONObject(index)
                                val email = jsonObject.getString("email")
                                val inputMail = emailText.text.toString()
                                if(inputMail == email){
                                    activity?.runOnUiThread {
                                        sharedPreferences?.edit()
                                            ?.putString("USER_NAME", jsonObject.getString("first_name"))
                                            ?.putString("USER_EMAIL", emailText.text.toString())
                                            ?.putString("USER_IMAGE", jsonObject.getString("avatar"))
                                            ?.apply()

                                        findNavController().navigate(R.id.action_login_dest_to_shop_dest, null)
                                    }
                                    found = true
                                    break
                                }
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                        if (!found) {
                            Snackbar.make(view, userNotFound, Snackbar.LENGTH_SHORT)
                                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_FADE)
                                .setAction(snackbarText) {}.show()
                        }
                    }
                })
    }
}