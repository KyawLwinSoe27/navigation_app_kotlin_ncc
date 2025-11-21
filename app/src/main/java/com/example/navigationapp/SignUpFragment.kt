package com.example.navigationapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.navigationapp.databinding.FragmentSignUpBinding
import org.json.JSONObject

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.loginLink.text = HtmlCompat.fromHtml("<u>Login</u>", HtmlCompat.FROM_HTML_MODE_LEGACY)
        binding.loginLink.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }
        binding.btnSignUpClear.setOnClickListener {
            binding.apply {
                editSignUpEmail.setText("")
                editSignUpUsername.setText("")
                editSignUpPassword.setText("")
                editFirstName.setText("")
                editLastName.setText("")
            }
        }

        binding.btnSignUpInSignUp.setOnClickListener {
            signUpAction()
        }



        return binding.root

    }

    private fun signUpAction() {
        val email = binding.editSignUpEmail.text.toString()
        val username = binding.editSignUpUsername.text.toString()
        val password = binding.editSignUpPassword.text.toString()
        val firstName = binding.editFirstName.text.toString()
        val lastName = binding.editLastName.text.toString()

        if (email.isEmpty()) {
            binding.editSignUpEmail.error = "Email is required"
            binding.editSignUpEmail.requestFocus()
            return
        }

        if (username.isEmpty()) {
            binding.editSignUpUsername.error = "Username is required"
            binding.editSignUpUsername.requestFocus()
            return
        }

        if (password.isEmpty()) {
            binding.editSignUpPassword.error = "Password is required"
            binding.editSignUpPassword.requestFocus()
            return
        }

        if (firstName.isEmpty()) {
            binding.editFirstName.error = "First name is required"
            binding.editFirstName.requestFocus()
            return
        }

        if (lastName.isEmpty()) {
            binding.editLastName.error = "Last name is required"
            binding.editLastName.requestFocus()
            return
        }

//        Toast.makeText(context, "Sign-Up Successful", Toast.LENGTH_LONG).show()
//        val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
//        findNavController().navigate(action)

        registerUser(firstName, lastName, email, username, password)

        // Here you can add further sign-up logic, such as saving the user data or navigating to another screen.
    }

    private fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        username: String,
        password: String
    ) {
      val url = "http://192.168.1.7:8000/registerUser.php"
        val request = object:StringRequest(Method.POST, url,
            Response.Listener{
                response ->
                Log.d("SignUpFragment", "Response: $response")
//                val obj = JSONObject(response)
                val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                findNavController().navigate(action)
            },
            Response.ErrorListener {
                error ->
                Log.d("SignUpFragment", "Error: $error")
                showAlert("Registration failed: $error")
            }
            ) {
            override fun getParams(): Map<String, String>? {
                return  mapOf("fname" to firstName,
                    "lname" to lastName,
                    "email" to email,
                    "username" to username,
                    "password" to password,
                    "remark" to "registering new user"
                    )
            }
        }
        Volley.newRequestQueue(context).add(request)
        Log.d("SignUpFragment", "Request: $request")
    }

    private fun showAlert(msg: String) {
        val alert = AlertDialog.Builder(requireContext())
        alert.setTitle("Warning").setMessage(msg).setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }.setCancelable(false)
        val alertDialog = alert.create()
        alertDialog.show()
    }
}