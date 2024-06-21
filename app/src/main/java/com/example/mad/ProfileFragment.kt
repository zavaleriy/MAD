package com.example.mad

import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.Profile
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mad.Adapters.GalleryAdapter
import com.example.mad.databinding.FragmentProfileBinding
import com.example.mad.managers.UserManager
import com.example.mad.managers.getBitmap
import com.example.mad.viewmodel.ProfileViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var photoPicker: ActivityResultLauncher<PickVisualMediaRequest>

    lateinit var userManager: UserManager
    var nick = ""
    var avatar = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)
        val repository = Repository(requireContext())
        viewModel = ProfileViewModel(repository)
        photoPicker = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            it?.let {
                val bitmap = getBitmap(requireContext().contentResolver, it)
                if (bitmap != null) viewModel.addImage(bitmap)
            }
        }

        userManager = UserManager(requireContext())

        lifecycleScope.launch {
            nick = userManager.userNickFlow.first()
            avatar = userManager.userAvatarFlow.first()
        }

        binding.nickname.text = nick
        binding.avatar.clipToOutline = true

        Picasso.get()
            .load(avatar)
            .into(binding.avatar)


        binding.logout.setOnClickListener {
            lifecycleScope.launch { userManager.Logout() }
            findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
        }

        val galleryAdapter = GalleryAdapter(
            click = { uri -> openPhoto(uri) },
            addClick = { openPhotoPicker() }
        )
        viewModel.getImages()
        binding.recyclerViewGallery.adapter = galleryAdapter
        lifecycleScope.launch {
            viewModel.uris.collect {
                galleryAdapter.submitList(it)
            }
        }

    }

    private fun openPhoto(uri: Uri) {
        val action = ProfileFragmentDirections.actionProfileFragmentToPhotoFragment(uri)
        findNavController().navigate(action)
    }

    private fun openPhotoPicker() {
        photoPicker.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

}