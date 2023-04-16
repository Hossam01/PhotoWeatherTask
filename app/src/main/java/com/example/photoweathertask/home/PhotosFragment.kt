package com.example.photoweathertask.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.photoweathertask.NewPhoto.view_state.LoadPhotoIntents
import com.example.photoweathertask.base.BaseFragment
import com.example.photoweathertask.databinding.PhotosFragmentBinding
import com.example.photoweathertask.home.adapter.PhotosAdapter
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class PhotosFragment : BaseFragment<PhotosFragmentBinding>() {
    private val viewModel by viewModels<PhotosViewModel>()

    lateinit var photosAdapter: PhotosAdapter
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PhotosFragmentBinding
        get() = PhotosFragmentBinding::inflate

    override fun setup() {
        viewModel.onEvent(LoadPhotoIntents.LoadPhotoIntent)
        subscribeLisnter()
        subscribeObserver()
    }

    private fun subscribeLisnter() {
        ui.btnNewPhoto.setOnClickListener {
            pickImage().setImageSelectedListener(object : ImagePickupListener {
                override fun onImageSelected(file: File?, uri: Uri) {
                    navigateTo(PhotosFragmentDirections.actionphotosFragmentTonewPhotoFragment(file?.path.toString()))
                }
            })
        }
    }


    fun subscribeObserver()
    {
        with(viewModel._uiState)
        {
            observe(viewLifecycleOwner)
            {
                if (it.weatherModel!=null)
                {
                    photosAdapter=PhotosAdapter()
                    it.weatherModel.toMutableList()?.let { it -> photosAdapter.addAll(it) }
                    ui.photoRecyclerView.adapter=photosAdapter
                }
            }
        }
    }
}