package com.pouyaa.imagediary

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.fragment_add_places.*
import java.io.IOException
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddPlacesFragment : Fragment() {

    private var cal = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_places, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addDateEditText.setOnClickListener { getActivity()?.let { it1 ->
            PickDate(addDateEditText).selectDate(
                it1
            )
        } }

        addImageTextView.setOnClickListener {  showChooseImageDialog()}

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
            if (requestCode == GALLERY) {
                if (data != null) {
                    val contentURI = data.data
                    try {
                        val selectedImageBitmap =
                            MediaStore.Images.Media.getBitmap(activity?.contentResolver, contentURI)

                        imageOfPlaceImageView.setImageBitmap(selectedImageBitmap)
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

            } else if (requestCode == CAMERA) {

                val thumbnail: Bitmap = data?.extras?.get("data") as Bitmap
                imageOfPlaceImageView.setImageBitmap(thumbnail)
            }
        } else if (resultCode == Activity.RESULT_CANCELED) {
            Log.e("Cancelled", "Cancelled")
        }
    }

    private fun choosePhotoFromGallery() {
        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {


                    if (report != null) {
                        if (report.areAllPermissionsGranted()) {

                            val galleryIntent = Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                            )

                            startActivityForResult(galleryIntent, GALLERY)

                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }).onSameThread()
            .check()
    }

    private fun takePhotoFromCamera() {

        Dexter.withActivity(activity)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    if (report != null) {
                        if (report.areAllPermissionsGranted()) {
                            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            startActivityForResult(intent, CAMERA)
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRationalDialogForPermissions()
                }
            }).onSameThread()
            .check()
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
//                        val uri = Uri.fromParts("package", pack, null)
//                        intent.data = uri
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


    companion object {
        private const val GALLERY = 1
        private const val CAMERA = 2
    }

}
