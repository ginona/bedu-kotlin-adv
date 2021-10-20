package com.gino.projectbedu.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.gino.projectbedu.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class AddressFragment :  BottomSheetDialogFragment() {

    private lateinit var btnCloseDirection : ImageView
    private lateinit var btnUpdateAddress : Button
    private lateinit var tvMyDirection: TextView
    private lateinit var tvDirection1 : TextView
    private lateinit var tvDirection2 : TextView
    private var sharedPreferences: SharedPreferences? = null
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val PERMISSION_ID = 33

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_address, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPreferences = this.activity?.getSharedPreferences("org.bedu.sharedpreferences", Context.MODE_PRIVATE)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        btnCloseDirection = view.findViewById(R.id.btnCloseDirection)
        btnUpdateAddress = view.findViewById(R.id.btnUpdateAddress)
        tvMyDirection = view.findViewById(R.id.tvMyDirection)
        tvDirection1 = view.findViewById(R.id.tvDirection1)
        tvDirection2 = view.findViewById(R.id.tvDirection2)

        //Setting shared preferences
        tvMyDirection.text = sharedPreferences?.getString("MY_ADDRESS","")
        tvDirection1.text = sharedPreferences?.getString("OLD_ADDRESS1","")
        tvDirection2.text = sharedPreferences?.getString("OLD_ADDRESS2","")

        btnCloseDirection.setOnClickListener {
            dismiss()
        }

        btnUpdateAddress.setOnClickListener {
            getLocation()
        }

    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->

                    val geocoder = Geocoder(requireContext(), Locale.getDefault())
                    val addresses  = location.latitude.let {
                        geocoder.getFromLocation(it.toDouble(), location.longitude.toDouble(),1)
                    }
                    val actualAddress = addresses?.get(0)?.getAddressLine(0)
                    if(tvMyDirection.text.toString() != actualAddress){
                        tvDirection2.text = tvDirection1.text.toString()
                        tvDirection1.text = tvMyDirection.text.toString()
                        tvMyDirection.text = actualAddress
                        sharedPreferences?.edit()
                            ?.putString("MY_ADDRESS",tvMyDirection.text.toString())
                            ?.putString("OLD_ADDRESS1",tvDirection1.text.toString())
                            ?.putString("OLD_ADDRESS2",tvDirection2.text.toString())
                            ?.apply()
                    }
                }
            }
        } else{
            //Asking for permissions
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSION_ID
            )
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun checkPermissions(): Boolean {
        return checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) && checkGranted(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun checkGranted(permission: String): Boolean{
        return ActivityCompat.checkSelfPermission(requireContext(), permission) == PackageManager.PERMISSION_GRANTED
    }
}