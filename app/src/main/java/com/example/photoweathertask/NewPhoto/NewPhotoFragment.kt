package com.example.photoweathertask.NewPhoto

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.photoweathertask.NewPhoto.view_state.NewPhotoIntents
import com.example.photoweathertask.R
import com.example.photoweathertask.base.BaseFragment
import com.example.photoweathertask.base.helper.LoadImage
import com.example.photoweathertask.databinding.NewPhotoFragmentBinding
import com.example.photoweathertask.util.getImageUri
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewPhotoFragment :BaseFragment<NewPhotoFragmentBinding>() {

    private val viewModel by viewModels<NewPhotoViewModel>()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> NewPhotoFragmentBinding
        get() = NewPhotoFragmentBinding::inflate

    override fun setup() {
        subscribeObserver()
        subscribeListener()
    }


    fun subscribeListener()
    {
        ui.btnNewPhoto.setOnClickListener{
            viewModel.onEvent(NewPhotoIntents.NewPhotoIntent)
        }

        ui.btnSharePhoto.setOnClickListener {
            val image =  takeScreenshot(ui.imageContainer,2000,1050)
            shareImage(context?.getImageUri(image))
            viewModel.onEvent(NewPhotoIntents.SavePhotoIntent("",context?.getImageUri(image)?.path.toString()))
        }
    }
    fun subscribeObserver()
    {
        with(viewModel.pathData)
        {
            observe(viewLifecycleOwner)
            {
                LoadImage.loadImage(requireContext(),it,R.drawable.weather,ui.weatherPhoto)
            }
        }

        with(viewModel._uiState)
        {
            observe(viewLifecycleOwner)
            {
                if (it!=null){
                ui.locationNameTv.setText(it.weatherModel?.name+","+it.weatherModel?.country)
                ui.tempTv.setText(it.weatherModel?.temp_c.toString())
                ui.tempStatusTv.setText(it.weatherModel?.text.toString())
                LoadImage.loadImage(requireContext(),"http://"+it.weatherModel?.icon,R.drawable.weather,ui.temperatureStatusIv)
                    ui.btnSharePhoto.visibility=View.VISIBLE
                    ui.btnNewPhoto.visibility=View.GONE
                }
            }
        }
    }


    fun takeScreenshot(view: View, height: Int, width: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bgDrawable = view.background
        if (bgDrawable != null) {
            bgDrawable.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }
        view.draw(canvas)
        return bitmap
    }

    fun shareImage(bitmapUri: Uri?) {
        bitmapUri.let {
            // Create a sharing intent
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_SEND
                flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                putExtra(Intent.EXTRA_STREAM,bitmapUri)
            }
            // Launch the intent letting the user choose which app to share with
            startActivity(Intent.createChooser(intent, getString(R.string.share)))
        }
    }
}

