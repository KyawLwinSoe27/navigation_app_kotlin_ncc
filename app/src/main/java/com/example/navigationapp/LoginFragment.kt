package com.example.navigationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.example.navigationapp.databinding.FragmentLoginBinding

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

        if(username=="su su" && password == "123454") {
            Toast.makeText(context, "Login Successful", Toast.LENGTH_LONG).show()
            return
        } else {
            Toast.makeText(context, "Login Failed", Toast.LENGTH_LONG).show()
            return
        }

        // Here you can add further login logic, such as authentication

        // For demonstration, navigate to HomeFragment on successful login
        val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()
        findNavController().navigate(action)
    }
}