package com.pankaj.cabs
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_enter_details.*
import kotlinx.android.synthetic.main.fragment_enter_details.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_otp.view.*
class enterDetails : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootview=inflater.inflate(R.layout.fragment_enter_details, container, false)
        rootview.launchMap.setOnClickListener(View.OnClickListener {

            var sPref=this.activity?.getSharedPreferences("oh",Context.MODE_PRIVATE)
            var phone=sPref!!.getString("isLoggedIn","0")
            sPref?.edit()!!.putString("chalu","111").apply()
            var user=User(rootview.name.text.toString(),rootview.email.text.toString(),rootview.address.text.toString(),phone)
            val dbref:DatabaseReference=FirebaseDatabase.getInstance().reference
            dbref.child("users").child(phone).push().setValue(user)
            startActivity(Intent(context,MapsActivity::class.java))
        })
        return rootview
    }
}
