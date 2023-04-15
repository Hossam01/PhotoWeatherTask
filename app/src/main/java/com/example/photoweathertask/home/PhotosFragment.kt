package com.example.photoweathertask.home

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.photoweathertask.base.BaseFragment
import com.example.photoweathertask.databinding.PhotosFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class PhotosFragment : BaseFragment<PhotosFragmentBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> PhotosFragmentBinding
        get() = PhotosFragmentBinding::inflate

    override fun setup() {

        ui.btnNewPhoto.setOnClickListener {
            pickImage().setImageSelectedListener(object :ImagePickupListener{
                override fun onImageSelected(file: File?, uri: Uri) {
                    navigateTo(PhotosFragmentDirections.actionphotosFragmentTonewPhotoFragment(file?.path.toString()))
                }
            })
        }
    }
}