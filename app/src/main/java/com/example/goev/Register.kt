package com.example.goev

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.goev.databinding.ActivityRegisterBinding

class Register : AppCompatActivity(){

//    var name: String? = ""
//    var email: String? = ""
//    var password: String? = ""
//    var reenterPassword: String? =""

    private lateinit var binding : ActivityRegisterBinding
    private val URL: String = "http://10.0.2.2/dbconnects/register.php"
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        binding.registerButton.setOnClickListener {

        }
    }

//    fun save(view: View?) {
//        name = binding.editRegisterName.text.toString().trim { it <= ' ' }
//        email = binding.editRegisterEmail.text.toString().trim { it <= ' ' }
//        password = binding.editRegisterPassword.text.toString().trim { it <= ' ' }
//        reenterPassword = binding.editRegisterPassword2.text.toString().trim { it <= ' ' }
//        if (password != reenterPassword) {
//            Toast.makeText(this, "Password Mismatch", Toast.LENGTH_SHORT).show()
//        } else if (name != "" && email != "" && password != "") {
//            val stringRequest: StringRequest = object : StringRequest(
//                Request.Method.POST, URL,
//                Response.Listener { response ->
//                    binding.registerStatus.text = response.toString()
//                    Log.d("Register",response)
//                    if (response == "success") {
//                        binding.registerStatus.text = "Successfully registered."
//                        binding.registerButton.isClickable = false
//                    } else if (response == "failure") {
//                        binding.registerStatus.text = "Something went wrong!"
//                    }
//                },
//                Response.ErrorListener { error ->
//                    Toast.makeText(
//                        applicationContext,
//                        error.toString().trim { it <= ' ' },
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }) {
//                @Throws(AuthFailureError::class)
//                override fun getParams(): Map<String, String>? {
//                    val data: MutableMap<String, String> = HashMap()
//                    data["name"] = name!!
//                    data["email"] = email!!
//                    data["password"] = password!!
//                    return data
//                }
//            }
//            val requestQueue = Volley.newRequestQueue(applicationContext)
//            requestQueue.add(stringRequest)
//        }
//    }
//
//    fun login(view: View?) {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(intent)
//        finish()
//    }

}