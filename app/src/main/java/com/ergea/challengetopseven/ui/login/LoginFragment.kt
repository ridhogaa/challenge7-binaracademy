package com.ergea.challengetopseven.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.ergea.challengetopseven.R
import com.ergea.challengetopseven.data.local.database.user.UserEntity
import com.ergea.challengetopseven.databinding.FragmentLoginBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by activityViewModels()
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getDataStoreIsLogin().observe(viewLifecycleOwner) {
            if (it == true) {
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        toRegister()
        toLogin()
    }

    private fun toLogin() {
        binding.btnLogin.setOnClickListener { view ->
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.flag(email, password)
            if (email.isNotEmpty() || password.isNotEmpty()) {
                viewModel.user.observe(viewLifecycleOwner) {
                    if (it != null) {
                        viewModel.setIsLogin(true)
                        viewModel.setId(it.id_user!!)
                        viewModel.setUsername(it.username!!)
                        Toast.makeText(
                            requireContext(),
                            "Login Success as a ${it.email}",
                            Toast.LENGTH_SHORT
                        ).show()
                        view.findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    } else {
                        Toast.makeText(requireContext(), "User Not Found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Field is empty", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun toRegister() {
        binding.textBpa.setOnClickListener {
            it.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}