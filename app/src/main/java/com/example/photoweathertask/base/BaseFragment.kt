package com.example.photoweathertask.base

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.photoweathertask.R
import com.example.photoweathertask.base.pick_images.convertBitmapToFile
import java.io.File
import java.io.FileNotFoundException
import java.util.*

abstract class BaseFragment <T : ViewBinding> : Fragment() {
    private val emptyNavigationIcon: Int = -1
    private var _binding: ViewBinding? = null
    var _savedInstanceState: Bundle? = null

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T

    @Suppress("UNCHECKED_CAST")
    protected val ui: T
        get() = _binding as T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _savedInstanceState=savedInstanceState
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onPause() {
        super.onPause()

    }

    protected abstract fun setup()

    protected fun setToolbar(
        mToolbar: Toolbar,
        title: String = "",
        setHomeAsUpEnabled: Boolean = true,
        navigationIconDrawable: Int = emptyNavigationIcon,
        action: () -> Unit = {}
    ) {
        if (navigationIconDrawable != emptyNavigationIcon)
            mToolbar.setNavigationIcon(navigationIconDrawable)
        mToolbar.title = title
        (activity as AppCompatActivity).setSupportActionBar(mToolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(
            setHomeAsUpEnabled
        )
        mToolbar.setNavigationOnClickListener {
            action()
            navigateUp()
        }
    }

    protected fun navigateTo(navDirections: NavDirections) {
        findNavController().navigate(navDirections)
    }

    protected fun navigateUp() {

        findNavController().navigateUp()
    }

    fun <T> Fragment.setNavigationResult(result: T, key: String) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
        findNavController().popBackStack()
    }

    fun <T> Fragment.getNavigationResultLiveData(key: String ) =
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)


    protected fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    var selectedImgFile: File? = null
    var type: String = ""
    var uri: Uri? = null

    private var getGalleryImage = registerForActivityResult(ActivityResultContracts.GetContent()) {

        val bitmap = if (Build.VERSION.SDK_INT < 28) {
            try {
                MediaStore.Images.Media.getBitmap(activity?.contentResolver, it)
            } catch (e: FileNotFoundException) {
                null
            } catch (r: NullPointerException) {
                null
            }
        } else {
            try {
                val source = ImageDecoder.createSource(requireActivity().contentResolver!!, it!!)
                ImageDecoder.decodeBitmap(source)
            } catch (e: FileNotFoundException) {
                null
            } catch (r: NullPointerException) {
                null
            }
        }


        val calendar = Calendar.getInstance()

        selectedImgFile = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            calendar.timeInMillis.toString() + "_selectedImg.jpg"
        )

        bitmap?.let { it1 ->
            convertBitmapToFile(selectedImgFile!!, it1)
            val contentURI = FileProvider.getUriForFile(
                requireActivity(),
                requireActivity().packageName.toString() + ".provider",
                selectedImgFile!!
            )
            type = requireActivity().contentResolver.getType(contentURI).toString()
            uri = it
            imageSelectListener?.onImageSelected(
                selectedImgFile!!,
                it!!
            )
        }
    }

    private var cameraCaptureImage =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                try {
                    MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
                } catch (e: FileNotFoundException) {
                    null
                } catch (r: NullPointerException) {
                    null
                }

            } else {
                try {
                    val source =
                        ImageDecoder.createSource(requireActivity().contentResolver!!, uri!!)
                    ImageDecoder.decodeBitmap(source)
                } catch (e: FileNotFoundException) {
                    null
                } catch (r: NullPointerException) {
                    null
                }
            }

            val calendar = Calendar.getInstance()

            val selectedImgFile = File(
                requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                calendar.timeInMillis.toString() + "_selectedImg.jpg"
            )

            bitmap?.let { it1 ->
                convertBitmapToFile(selectedImgFile, it1)
                val contentURI = FileProvider.getUriForFile(
                    requireActivity(),
                    requireActivity().packageName.toString() + ".provider",
                    selectedImgFile
                )

                val type = requireActivity().contentResolver.getType(contentURI)
                imageSelectListener?.onImageSelected(
                    selectedImgFile, uri!!
                )
            }
        }

    fun pickImage(): BaseFragment<T> {
        if (ActivityCompat.checkSelfPermission(
                requireActivity().applicationContext,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireActivity().applicationContext,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                1002
            )
        } else {
            selectOption()
        }
        return this
    }

    private fun selectOption() {
        val items = arrayOf<CharSequence>(getString(R.string.camera), getString(R.string.gallery))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(getString(R.string.options))
        builder.setItems(items) { _, which ->
            if (items[which] === getString(R.string.camera)) useCamera() else if (items[which] === getString(
                    R.string.gallery
                )
            ) useGallery()
        }
        builder.show()
    }
    private fun useGallery() {
        getGalleryImage.launch("image/*")
    }

    private fun useCamera() {
        val calendar = Calendar.getInstance()
        val selectedImgFile = File(
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            calendar.timeInMillis.toString() + "_selectedImg.jpg"
        )
        uri = FileProvider.getUriForFile(
            requireActivity(),
            requireActivity().packageName.toString() + ".provider",
            selectedImgFile
        )
        cameraCaptureImage.launch(uri)

    }

    var imageSelectListener: ImagePickupListener? = null

    fun setImageSelectedListener(pickupListener: ImagePickupListener) {
        imageSelectListener = pickupListener
    }

    interface ImagePickupListener {
        fun onImageSelected(file: File?, uri: Uri)
    }

}