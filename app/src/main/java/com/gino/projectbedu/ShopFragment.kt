package com.gino.projectbedu

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.ListFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Tasks
import org.bedu.listdetailfragment.Product
import org.bedu.listdetailfragment.RecyclerAdapter
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 * Use the [ShopFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShopFragment : Fragment() {
    private lateinit var mAdapter : RecyclerAdapter
    private var listener : (Product) ->Unit = {}
    private lateinit var recyclerProducts: RecyclerView

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
        /*Look here*/
        recyclerProducts.setOnClickListener{
            Toast.makeText(
                requireActivity(),
                "${view.findViewById<EditText>(R.id.tvDescription)}", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setUpRecyclerView(){

        recyclerProducts.setHasFixedSize(true)
        recyclerProducts.layoutManager = LinearLayoutManager(activity)
        mAdapter = RecyclerAdapter( requireActivity(), getProducts(), listener)
        recyclerProducts.adapter = mAdapter
    }

    private fun getProducts(): MutableList<Product>{
        var products:MutableList<Product> = ArrayList()

        products.add(Product("Control ps5", "Disponible el 20 de noviembre", "$1400",4.6f,R.drawable.control))
        products.add(Product("Intel core i9", "10ma Generación", "$9800",4.4f,R.drawable.corei9))
        products.add(Product("Lector Kobo", "Disponible Prime", "$2235",3.8f,R.drawable.kobo))
        products.add(Product("Audífonos Sony xm3", "Noise Cancelling", "$6449",4.8f,R.drawable.xm3))

        return products
    }

    /*private fun getJsonDataFromAsset(context: Context, fileName: String = "products.json"): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getProducts(context: Context): List<Product> {
        val jsonString = getJsonDataFromAsset(context)
        val listProductType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(jsonString, listProductType)
    }*/
}