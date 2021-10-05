package com.gino.projectbedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gino.projectbedu.fragments.DetailFragment

class DetailActivity : AppCompatActivity() {

    companion object {
        const val PRODUCT = "PRODUCT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val product = intent.getParcelableExtra<Product>(PRODUCT)
        val detailFragment = supportFragmentManager.findFragmentById(R.id.fragmentDetail) as? DetailFragment
        detailFragment?.showProduct(product!!)
    }
}