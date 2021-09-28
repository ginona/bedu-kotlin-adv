package com.gino.projectbedu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ShopFragment : Fragment() {
    private lateinit var mAdapter : RecyclerAdapter
    private var listener : (Product) ->Unit = {}
    private lateinit var recyclerProducts: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

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
        setUpRecyclerView()
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
        }
    }

    /**
     * Seteamos nuestro recyclerView
     */
    private fun setUpRecyclerView(){
        recyclerProducts.setHasFixedSize(true)
        recyclerProducts.layoutManager = LinearLayoutManager(activity)
        mAdapter = RecyclerAdapter(requireActivity(), getProducts(requireContext()), listener)
        recyclerProducts.adapter = mAdapter
    }

    private fun getProducts(): MutableList<Product>{
        var products:MutableList<Product> = ArrayList()

        products.add(Product(1,"Control ps5",4.7f,"7000","Soy una descripción", "Soy una categoria","https://fakestoreapi.com/img/71kWymZ+c+L._AC_SX679_.jpg"))

        return products
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
    fun getProducts(context: Context): MutableList<Product> {
        val jsonString = getJsonDataFromAsset(context)
        val listProductType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(jsonString, listProductType)
    }
}