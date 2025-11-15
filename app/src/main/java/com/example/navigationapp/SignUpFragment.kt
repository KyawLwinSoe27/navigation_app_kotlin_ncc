package com.example.navigationapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import com.example.navigationapp.databinding.FragmentSignUpBinding

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

        Toast.makeText(context, "Sign-Up Successful", Toast.LENGTH_LONG).show()
        val action = SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
        findNavController().navigate(action)

        // Here you can add further sign-up logic, such as saving the user data or navigating to another screen.
    }
}