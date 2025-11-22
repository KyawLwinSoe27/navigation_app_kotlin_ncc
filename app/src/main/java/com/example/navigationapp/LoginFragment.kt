package com.example.navigationapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.navigationapp.databinding.FragmentLoginBinding
import org.json.JSONObject

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)
        binding.signUp.setText(
            HtmlCompat.fromHtml("<u>Sign Up</u>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        )
        binding.signUp.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }
        binding.btnClear.setOnClickListener {
            binding.editLoginUsername.setText("")
            binding.editLoginPassword.setText("")
        }

        binding.btnLogin.setOnClickListener {
            loginAction()
        }

        return binding.root
    }

    private fun loginAction() {
        val username = binding.editLoginUsername.text.toString()
        val password = binding.editLoginPassword.text.toString()

        if (username.isEmpty()) {
            binding.editLoginUsername.error = "Email is required"
            binding.editLoginUsername.requestFocus()
            return
        }

        if (password.isEmpty()) {
            binding.editLoginPassword.error = "Password is required"
            binding.editLoginPassword.requestFocus()
            return
        }

//        if(username=="su su" && password == "123454") {
//            Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
//            return
//        } else {
//            Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
//            return
//        }

        loginUser(username, password)
        // For demonstration, navigate to HomeFragment on successful login
//        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
//        findNavController().navigate(action)
    }

    private fun loginUser(
        username: String,
        password: String
    ) {
        val url = "http://192.168.1.7:8000/loginUser.php"
        val request = object:StringRequest(Method.POST, url,
            Response.Listener{
                    response ->
                Log.d("LoginFragment", "Response: $response")
                // Handle the response from the server
                val obj = JSONObject(response)
                if(obj.getString("message") == "Login Successfully.") {
                    val user = obj.get("user") as JSONObject
                    val st = "Hello, ${user.getString("firstname")} ${user.getString("lastname")} ${user.getString("email")}"
                    Toast.makeText(context, st, Toast.LENGTH_LONG).show()

                    val intent = Intent(context, UserActivity::class.java)
                    intent.putExtra("id", user.getInt("id"))
                    intent.putExtra("firstname", user.getString("firstname"))
                    intent.putExtra("lastname", user.getString("lastname"))
                    intent.putExtra("username", user.getString("username"))
                    startActivity(intent)
                    activity?.finish()
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
                }
            },
            Response.ErrorListener {
                    error ->
                Log.d("LoginFragment", "Error: $error")
                Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getParams(): Map<String, String>? {
                return  mapOf(
                    "username" to username,
                    "password" to password
                )
            }
        }
        Volley.newRequestQueue(context).add(request)
    }
}