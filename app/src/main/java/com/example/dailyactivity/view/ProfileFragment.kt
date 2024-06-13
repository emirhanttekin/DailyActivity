package com.example.dailyactivity.view

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.dailyactivity.R
import com.example.dailyactivity.database.AppDatabase
import com.example.dailyactivity.databinding.FragmentProfileBinding
import com.example.dailyactivity.repository.UserRepository
import com.example.dailyactivity.utils.SharedPreferencesHelper
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var userRepository: UserRepository
    private val REQUEST_IMAGE_CAPTURE = 1
    private val REQUEST_IMAGE_PICK = 2
    private lateinit var currentPhotoPath: String
    private val userId: Int by lazy {
        requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            .getInt("userId", 0)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        userRepository = UserRepository(AppDatabase.getInstance(requireContext()))
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userName = SharedPreferencesHelper.getUserName(requireContext())
        val userEmail = SharedPreferencesHelper.getUserEmail(requireContext())
        binding.username.text = userName
        binding.mail.text = userEmail

        binding.moreOptions.setOnClickListener {
            showPopupMenu(it)
        }

        // Load the user's photo if it exists
        val userPhotoPath = SharedPreferencesHelper.getUserPhoto(requireContext())
        if (userPhotoPath != null) {
            val uri = Uri.parse(userPhotoPath)
            binding.profileEllipse.setImageURI(uri)
        }

        binding.addPhotoButton.setOnClickListener {
            showPhotoOptions()
        }

        // Update user photo
        lifecycleScope.launch {
            userRepository = UserRepository(AppDatabase.getInstance(requireContext()))
            userRepository.getUserById(userId)?.let { user ->
                user.photo = userPhotoPath // Update photo with the userPhotoPath

            }
        }
    }




    private fun showPhotoOptions() {
        val popupMenu = PopupMenu(requireContext(), binding.addPhotoButton)
        popupMenu.menuInflater.inflate(R.menu.photo_options_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_take_photo -> {
                    dispatchTakePictureIntent()
                    true
                }

                R.id.action_choose_from_gallery -> {
                    dispatchChoosePictureIntent()
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            val photoFile: File? = try {
                createImageFile()
            } catch (ex: IOException) {
                null
            }
            photoFile?.also {
                val photoURI: Uri = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.dailyactivity.fileprovider",
                    it
                )
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }

    private fun dispatchChoosePictureIntent() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val bitmap = BitmapFactory.decodeFile(currentPhotoPath)
                    binding.profileEllipse.setImageBitmap(bitmap)
                    savePhotoToUser(currentPhotoPath)
                }

                REQUEST_IMAGE_PICK -> {
                    val selectedImage: Uri? = data?.data
                    selectedImage?.let {
                        val inputStream = requireActivity().contentResolver.openInputStream(it)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        binding.profileEllipse.setImageBitmap(bitmap)
                        val photoPath = saveImageToInternalStorage(bitmap)
                        savePhotoToUser(photoPath)
                    }
                    // Update user photo with the selected image
                    selectedImage?.let { uri ->
                        lifecycleScope.launch {
                            userRepository.updatePhoto(userId, uri.toString())
                        }
                    }
                }
            }
        }
    }


    private fun savePhotoToUser(photoPath: String?) {

    }

    private fun saveImageToInternalStorage(bitmap: Bitmap): String {
        val filename = "profile_${userId}.jpg"
        val file = File(requireContext().filesDir, filename)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        } finally {
            fos?.close()
        }
        return file.absolutePath
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    // Handle settings action
                    true
                }

                R.id.action_logout -> {
                    findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }
}
