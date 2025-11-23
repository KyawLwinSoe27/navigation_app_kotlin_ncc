package com.example.navigationapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.navigationapp.databinding.FragmentWelcomeUserBinding

class WelcomeUserFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeUserBinding.inflate(inflater, container, false)

        val id = arguments?.getInt("id")
        val firstName = arguments?.getString("firstName")
        val lastName = arguments?.getString("lastName")
        val username = arguments?.getString("username")

        "Welcome \n Id: $id  \n FirstName : $firstName \n LastName: $lastName".also { binding.txtWelcome.text = it };

        return binding.root
    }
}