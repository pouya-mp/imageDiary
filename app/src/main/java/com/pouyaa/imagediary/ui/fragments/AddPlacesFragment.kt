package com.pouyaa.imagediary.ui.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.pouyaa.imagediary.DataBaseHandler
import com.pouyaa.imagediary.PickDate
import com.pouyaa.imagediary.R
import com.pouyaa.imagediary.SaveImageToInternalStorage
import com.pouyaa.imagediary.databinding.FragmentAddPlacesBinding
import com.pouyaa.imagediary.model.PlaceModel
import com.pouyaa.imagediary.utils.CustomTimer
import com.pouyaa.imagediary.viewmodel.AddPlacesViewModel
import kotlinx.android.synthetic.main.fragment_add_places.*
import timber.log.Timber
import java.io.IOException

/**
 * A simple [Fragment] subclass.
 */
class AddPlacesFragment : Fragment() {

    private var _binding: FragmentAddPlacesBinding? = null
    private lateinit var timer: CustomTimer
    private val viewModel: AddPlacesViewModel by viewModels()

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding
        get() = _binding!!

    private var savedImageOnInternalStorage: Uri? = null
    private var selectedPlaceLatitude: Double = 0.0
    private var selectedPlaceLongitude: Double = 0.0
    private var updatePlaceWithId: Int? = null
    private var checkImage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,

        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddPlacesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        timer = CustomTimer(lifecycle)

        savedInstanceState?.let {
            timer.secondsCount = it.getInt(SAVED_PASSED_SECONDS, 0)
        }

        val args = AddPlacesFragmentArgs.fromBundle(requireArguments())

        args.place?.let {
            checkImage = true
            savedImageOnInternalStorage = Uri.parse(args.place?.image)
            updatePlaceWithId = args.place?.id
        }

        binding.apply {
            place = args.place
            invalidateAll()
            imageOfPlaceImageView.setImageURI(savedImageOnInternalStorage)
            addImageTextView.text = getString(R.string.changeImage)
            saveButton.text = getString(R.string.saveChanges)
        }

        addDateEditText.setOnClickListener {
            activity?.let { it1 ->
                PickDate(addDateEditText).selectDate(
                    it1
                )
            }
        }

        addImageTextView.setOnClickListener { showChooseImageDialog() }

        saveButton.setOnClickListener {
            if (checkIsEmptyOnAddPlaceFields()) {
                saveToDatabase()
            }
        }

    }


    private fun showChooseImageDialog() {
        val chooseImageDialog = AlertDialog.Builder(activity)
        chooseImageDialog.setTitle(getString(R.string.selectImageAlertDialog))
        val dialogItems = arrayOf(
            getString(R.string.selectPhotoFromLibrary),
            getString(R.string.CapturePhotoFromCamera)
        )
        chooseImageDialog.setItems(dialogItems) { _, which ->
            when (which) {
                0 -> choosePhotoFromGallery()
                1 -> takePhotoFromCamera()
            }
        }
        chooseImageDialog.show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY_PERMISSION_CODE) {
                if (data != null) {
                    val contentURI = data.data
                    try {
                        val selectedImageBitmap =
                            MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)

                        binding.imageOfPlaceImageView.setImageBitmap(selectedImageBitmap)
                        savedImageOnInternalStorage = SaveImageToInternalStorage(
                            IMAGE_DIRECTORY,
                            ContextWrapper(activity)
                        ).saveImage(selectedImageBitmap)
                        checkImage = true
                        Log.e("Saved Image : ", "Path :: $savedImageOnInternalStorage")
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(
                            activity,
                            getString(R.string.failed),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }

            } else if (requestCode == CAMERA_PERMISSION_CODE) {

                val thumbnail: Bitmap = data?.extras?.get("data") as Bitmap
                binding.imageOfPlaceImageView.setImageBitmap(thumbnail)
                savedImageOnInternalStorage = SaveImageToInternalStorage(
                    IMAGE_DIRECTORY,
                    ContextWrapper(activity)
                ).saveImage(thumbnail)
                checkImage = true
                Log.e("Saved Image : ", "Path :: $savedImageOnInternalStorage")
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Cancelled", "Cancelled")
        }
    }

    private fun choosePhotoFromGallery() {

        context?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                && (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED)
            ) {


                val galleryIntent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )

                startActivityForResult(
                    galleryIntent,
                    GALLERY_PERMISSION_CODE
                )

            } else {

                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ),
                        GALLERY_PERMISSION_CODE
                    )
                }
            }
        }

    }

    private fun takePhotoFromCamera() {

        context?.let {
            if (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
                && (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED)
            ) {


                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(
                    intent,
                    CAMERA_PERMISSION_CODE
                )


            } else {

                activity?.let { it1 ->
                    ActivityCompat.requestPermissions(
                        it1,
                        arrayOf(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA
                        ),
                        CAMERA_PERMISSION_CODE
                    )
                }
            }
        }


    }

    private fun showRationalDialogForPermissions() {
        activity?.let {
            androidx.appcompat.app.AlertDialog.Builder(it)
                .setMessage(getString(R.string.goToSettingsMessage))
                .setPositiveButton(
                    getString(R.string.goToSettings)
                ) { _, _ ->
                    try {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        e.printStackTrace()
                    }
                }
                .setNegativeButton(getString(R.string.cancel)) { dialog,
                                                                 _ ->
                    dialog.dismiss()
                }.show()
        }
    }

    private fun checkIsEmptyOnAddPlaceFields(): Boolean {
        var result = false
        when {

            binding.addTitleEditText.text.isNullOrEmpty() -> {
                Toast.makeText(activity, getString(R.string.pleaseEnterTitle), Toast.LENGTH_SHORT)
                    .show()
            }
            binding.addDescriptionEditText.text.isNullOrEmpty() -> {
                Toast.makeText(
                    activity,
                    getString(R.string.pleaseEnterDescription),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            binding.addDateEditText.text.isNullOrEmpty() -> {
                Toast.makeText(activity, getString(R.string.pleaseEnterDate), Toast.LENGTH_SHORT)
                    .show()
            }
            binding.addLocationEditText.text.isNullOrEmpty() -> {
                Toast.makeText(
                    activity,
                    getString(R.string.pleaseSelectLocation),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            !checkImage -> {
                Toast.makeText(activity, getString(R.string.pleaseAddImage), Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                result = true
            }
        }
        return result
    }

    private fun saveToDatabase() {

        var id = 0
        updatePlaceWithId?.let { id = it }

        val myPlaceModel = PlaceModel(
            id,
            binding.addTitleEditText.text.toString(),
            savedImageOnInternalStorage.toString(),
            binding.addDescriptionEditText.text.toString(),
            binding.addDateEditText.text.toString(),
            binding.addLocationEditText.text.toString(),
            selectedPlaceLatitude,
            selectedPlaceLongitude

        )


        val dbHandler = activity?.let { DataBaseHandler(it) }

        if (updatePlaceWithId == null) {
            dbHandler?.addMyPlace(myPlaceModel)?.let {
                if (it > 0) {
                    Toast.makeText(context, "Saved Place Successfully", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        } else {
            dbHandler?.updateMyPlace(myPlaceModel)?.let {
                if (it > 0) {
                    Toast.makeText(context, "Place Updated", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
            }
        }


    }

    companion object {
        private const val GALLERY_PERMISSION_CODE = 1
        private const val CAMERA_PERMISSION_CODE = 2
        private const val IMAGE_DIRECTORY = "ImageDiaryImages"

        private const val SAVED_PASSED_SECONDS = "SAVED_PASSED_SECONDS"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(SAVED_PASSED_SECONDS, timer.secondsCount)
        super.onSaveInstanceState(outState)
        Timber.i("onSaveInstanceState")
    }
}
