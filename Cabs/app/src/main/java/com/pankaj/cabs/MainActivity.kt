package com.pankaj.cabs

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        var sPref=getSharedPreferences("oh",Context.MODE_PRIVATE)
        var isLoggedin=sPref.getString("chalu","0")
        if(isLoggedin.equals("0")==false)
        {
            startActivity(Intent(this,MapsActivity::class.java))
        }


        var fragment:android.support.v4.app.Fragment=login()

        var fragmentTransaction=supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment,fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        Log.e("TAG","0")
    }


}

