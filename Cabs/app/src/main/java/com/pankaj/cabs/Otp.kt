package com.pankaj.cabs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_otp.*
import kotlinx.android.synthetic.main.fragment_otp.view.*


class Otp : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var rootView= inflater.inflate(R.layout.fragment_otp, container, false)

        var fragment:android.support.v4.app.Fragment=enterDetails()

        rootView.proceed.setOnClickListener(View.OnClickListener {

            var sPref=this.activity?.getSharedPreferences("oh",Context.MODE_PRIVATE)
            var phone=sPref!!.getString("isLoggedIn","0")
            val dbref: DatabaseReference = FirebaseDatabase.getInstance().reference

            dbref.child("users").child(phone).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists())
                    {
                        var sPref=activity?.getSharedPreferences("oh",Context.MODE_PRIVATE)

                        sPref?.edit()!!.putString("chalu","111").apply()
                        startActivity(Intent(activity,MapsActivity::class.java))
                    }
                    else
                    {
                        var fragmentTransaction=activity?.supportFragmentManager!!.beginTransaction()
                        //var fragmentTransaction=FragmentManager.beginTransaction()
                        fragmentTransaction.replace(R.id.myFragment,fragment)
                        fragmentTransaction.addToBackStack(null)
                        fragmentTransaction.commit()
                    }
                }


            })


        })
        return rootView
    }

}
