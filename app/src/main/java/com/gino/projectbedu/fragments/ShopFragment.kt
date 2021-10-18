package com.gino.projectbedu.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gino.projectbedu.activities.DetailActivity
import com.gino.projectbedu.domain.Product
import com.gino.projectbedu.R
import com.gino.projectbedu.adapters.RecyclerAdapter
import com.gino.projectbedu.services.ApiService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception

class ShopFragment : Fragment() {
    private lateinit var mAdapter : RecyclerAdapter
    private var listener : (Product) ->Unit = {}
    private lateinit var recyclerProducts: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView
    private val baseUrl = "https://fakestoreapi.com/products"
    private val url2 = "https://fakestoreapi.com/"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shop, container, false)
        recyclerProducts = view.findViewById(R.id.recyclerProducts)
        return view
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getProductsService()
    }

    fun setListener(l: (Product) ->Unit){
        listener = l
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity: Activity = requireActivity()
        bottomNavigationView = activity.findViewById(R.id.bottom_navigation)
        bottomNavigationView.visibility = View.VISIBLE
        /**
         * Agregamos un listener en cada producto de la lista, para que al hacer clic
         * se muestre el detalle del mismo.
         */
        setListener {
            val intent = Intent(requireActivity(), DetailActivity::class.java)
            intent.putExtra(DetailActivity.PRODUCT,it)
            startActivity(intent)
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    /**
     * Seteamos nuestro recyclerView
     */
    private fun setUpRecyclerView(products: List<Product>){
        recyclerProducts.setHasFixedSize(true)
        recyclerProducts.layoutManager = LinearLayoutManager(activity)
        mAdapter = RecyclerAdapter(requireActivity(), products, listener)
        recyclerProducts.adapter = mAdapter
    }

    private fun getProductsService() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call = Retrofit.Builder()
                    .baseUrl(url2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java).getProducts("products")
                val productsReceived = call.body()
                activity?.runOnUiThread{
                    if(call.isSuccessful){
                        setUpRecyclerView(productsReceived?: listOf<Product>())
                    }else{
                        Toast.makeText(requireContext(),"No es posible cargar los productos.",Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                Log.d("Error", e.toString())
            }
        }
    }

    /**
     * Método que sirve para parsear nuestro JSON
     * de tal forma que cada variable del mismo coincida con nuestro objeto Product.
     */
    private fun getJsonDataFromAsset(context: Context, fileName: String = "products.json"): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    /**
     * Método que recibe el contexto y retorna la lista de Products
     */
    fun getProductsFromFile(context: Context): List<Product> {
        val jsonString = getJsonDataFromAsset(context)
        val listProductType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(jsonString, listProductType)
    }
}