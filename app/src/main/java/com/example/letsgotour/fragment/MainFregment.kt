package com.example.letsgotour.fragment

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.letsgotour.databinding.FragmentMainFregmentBinding
import com.example.letsgotour.modal.ItemModal
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainFregment : Fragment() {
    lateinit var binding: FragmentMainFregmentBinding
    private lateinit var mDbRef: DatabaseReference
    var storageReference: StorageReference? = null
    private var gryUri: Uri? = null
    var imageKey: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainFregmentBinding.inflate(inflater, container, false)
        val view = binding.root

        clickEvent()

        return view
    }

    private fun clickEvent() {
        binding.btnSubmitItem.setOnClickListener {
            initView()
        }
        binding.txtSelectImage.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                100
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            if (resultCode == -1 && data != null) {
                gryUri = data.data
                binding.ImgSelect.setImageURI(gryUri)
//                Log.e(TAG, "onActivityResult: step-2 $gryUri")
                uploadImage()
            }
        }
    }

    private fun initView() {
        var name = binding.edtItemName.text.toString()
        var price = binding.edtItemPrice.text.toString()
        var startDes = binding.edtItemStartDes.text.toString()
        var endDes = binding.edtItemToEndDes.text.toString()
        var day = binding.edtItemDay.text.toString()
        var person = binding.edtItemPerson.text.toString()
        var description = binding.edtItemDescription.text.toString()
        val latitude : Float = java.lang.Float.valueOf(binding.edtItemLatitude.text.toString())
        val longitude : Float = java.lang.Float.valueOf(binding.edtItemLongitude.text.toString())
        if (name.isEmpty()) {
            binding.edtItemName.error = "Enter Name"
        } else if (price.isEmpty()) {
            binding.edtItemPrice.error = "Enter Price"
        } else if (startDes.isEmpty()) {
            binding.edtItemStartDes.error = "Enter StartDestination"
        } else if (endDes.isEmpty()) {
            binding.edtItemToEndDes.error = "Enter EndDestination"
        } else if (day.isEmpty()) {
            binding.edtItemDay.error = "Enter Day"
        } else if (person.isEmpty()) {
            binding.edtItemPerson.error = "Enter Person"
        } else if (description.isEmpty()) {
            binding.edtItemDescription.error = "Enter Description"
        } else if (latitude == null) {
            binding.edtItemLatitude.error ="Enter Latitude"
        }else if (longitude==null) {
            binding.edtItemLongitude.error ="Enter Longitude"
        }else if (gryUri==null) {
            Toast.makeText(context, "Select image", Toast.LENGTH_SHORT).show()
        } else {
            AddDatabase(name, price, startDes, endDes, day, person, description,latitude,longitude)
        }
    }

    private fun uploadImage() {
        if (gryUri != null) {
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()

            imageKey = UUID.randomUUID().toString()
            storageReference = FirebaseStorage.getInstance().reference

            var ref: StorageReference = storageReference!!.child(
                "Profile/$imageKey"
            )
            ref.putFile(gryUri!!)
                .addOnSuccessListener {
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Image Uploaded!!",
                            Toast.LENGTH_SHORT
                        )
                        .show()
                }
                .addOnFailureListener { e ->
                    progressDialog.dismiss()
                    Toast
                        .makeText(
                            context,
                            "Failed " + e.message,
                            Toast.LENGTH_LONG
                        )
                        .show()
                    Log.d("TAG", "uploadImage:${e.message} ")
                }
                .addOnProgressListener { taskSnapshot ->
                    val progress = ((100.0
                            * taskSnapshot.bytesTransferred
                            / taskSnapshot.totalByteCount))
                    progressDialog.setMessage(
                        ("Uploaded " + progress.toInt() + "%")
                    )
                }

        } else {

        }


    }

    private fun AddDatabase(
        name: String,
        price: String,
        startDes: String,
        endDes: String,
        day: String,
        person: String,
        description: String,
        latitude: Float,
        longitude: Float
    ) {
        mDbRef = FirebaseDatabase.getInstance().reference
        var uid = UUID.randomUUID()
        mDbRef.child("Item").child(uid.toString())
            .setValue(ItemModal(name, price, startDes, endDes, day, person, description, imageKey,uid.toString(),latitude,longitude))
        Toast.makeText(context, "Add package successfully ", Toast.LENGTH_SHORT).show()
        cleanText()
    }

    private fun cleanText() {
        binding.edtItemName.text=null
         binding.edtItemPrice.text=null
         binding.edtItemStartDes.text=null
         binding.edtItemToEndDes.text=null
         binding.edtItemDay.text=null
         binding.edtItemPerson.text=null
         binding.edtItemDescription.text=null
         binding.edtItemLatitude.text=null
         binding.edtItemLongitude.text=null
    }

}