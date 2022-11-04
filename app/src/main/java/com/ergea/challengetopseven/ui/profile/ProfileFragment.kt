package com.ergea.challengetopseven.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.ergea.challengetopseven.R
import com.ergea.challengetopseven.databinding.FragmentProfileBinding
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.math.log

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by activityViewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var image: String
    private lateinit var firebaseAnalytics: FirebaseAnalytics


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseAnalytics = Firebase.analytics
        viewModel.getId().observe(viewLifecycleOwner) {
            viewModel.getUserById(it)
            updateUser(it)
        }
        logout()
        observeUser()
        imageClick()
    }

    private fun updateUser(id: Int) {
        binding.btnUpdate.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val namaLengkap = binding.etNamaLengkap.text.toString().trim()
            val tanggalLahir = binding.etTanggalLahir.text.toString().trim()
            val alamat = binding.etAlamat.text.toString().trim()
            viewModel.updateUser(id, username, namaLengkap, tanggalLahir, alamat, image)
            it.findNavController().navigate(R.id.action_profileFragment_to_homeFragment)
            Toast.makeText(requireContext(), "Update Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeUser() {
        viewModel.user.observe(viewLifecycleOwner) { data ->
            binding.apply {
                if (data != null) {
                    etUsername.setText(data.username.toString())
                    etNamaLengkap.setText(data.namaLengkap.toString())
                    etTanggalLahir.setText(data.tanggalLahir.toString())
                    etAlamat.setText(data.alamat.toString())
                    if (data.gambar != "") {
                        Glide.with(requireContext()).load(data.gambar).into(binding.myPic)
                    }
                }
            }
        }
    }

    private fun imageClick() {
        binding.myPic.setOnClickListener {
            checkingPermissions()
        }
    }

    private fun logout() {
        binding.btnLogout.setOnClickListener {
            viewModel.removeIsLogin()
            viewModel.removeId()
            viewModel.removeUsername()
            Toast.makeText(requireContext(), "Logout", Toast.LENGTH_SHORT).show()
            it.findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }
    }

    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            Log.d("URI_IMG", result.toString())
            image = result.toString()
            binding.myPic.setImageURI(result)
        }
    private val REQUEST_CODE_PERMISSION = 100

    private fun checkingPermissions() {
        if (isGranted(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            openGallery()
        }
    }

    private fun openGallery() {
        requireActivity().intent.type = "image/*"
        galleryResult.launch("image/*")
    }

    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", requireActivity().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
