package com.example.mad

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.mad.databinding.FragmentPhotoBinding
import com.example.mad.viewmodel.PhotoViewModel

class PhotoFragment : Fragment(R.layout.fragment_photo) {

    private lateinit var binding: FragmentPhotoBinding
    private lateinit var viewModel: PhotoViewModel
    private lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPhotoBinding.bind(view)
        repository = Repository(requireContext())
        viewModel = PhotoViewModel(repository)

        val uri = arguments?.let { PhotoFragmentArgs.fromBundle(it).imageUri }

        if (uri != null) {
            binding.Photo.setImageURI(uri)

            // Delete
            binding.buttonDelete.setOnClickListener {
                deleteImage(uri)
            }

            // Close
            binding.buttonClose.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun deleteImage(uri: Uri) {
        val isDeleted = repository.deleteImage(uri)
        if (isDeleted) {
            viewModel.removeImage(uri)
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "Ошибка во время удаления изображения", Toast.LENGTH_SHORT).show()
        }
    }

}