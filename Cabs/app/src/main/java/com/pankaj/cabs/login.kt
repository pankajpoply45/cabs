package com.pankaj.cabs

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_login.view.*
import okhttp3.*
import java.io.IOException
//import android.support.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread




class login : Fragment(){

    private val mClient = OkHttpClient()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView=inflater.inflate(R.layout.fragment_login, container, false)
        var sPref=this.activity?.getSharedPreferences("oh",Context.MODE_PRIVATE)
        sPref?.edit()!!.putString("isLoggedIn",rootView.phone.text.toString()).apply()
        Log.e("TAG","PUT 1")
        rootView.sendOtp.setOnClickListener(View.OnClickListener {
            //send the otp



            var fragment:android.support.v4.app.Fragment=Otp()
            var sPref=this.activity?.getSharedPreferences("oh",Context.MODE_PRIVATE)
            sPref?.edit()!!.putString("isLoggedIn",rootView.phone.text.toString()).apply()
            var fragmentTransaction=activity?.supportFragmentManager!!.beginTransaction()
            fragmentTransaction.replace(R.id.myFragment,fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @Throws(IOException::class)
    fun post(url: String, callback: Callback): Call {
        val formBody = FormBody.Builder()
            .add("To","7404630464")
            .add("Body", "hello")
            .build()
        val request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()
        val response = mClient.newCall(request)
        response.enqueue(callback)
        return response
    }


}
