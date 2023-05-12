package com.example.goev

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.goev.databases.post.PostViewModel
import com.example.goev.databases.post.TkPostData
import com.example.goev.databinding.FragmentAddPostAdminBinding
import java.io.ByteArrayOutputStream

class AddPostAdmin : Fragment() {
    lateinit var imageView: ImageView
    lateinit var imageInBitmap: Bitmap
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentAddPostAdminBinding>(inflater,
            R.layout.fragment_add_post_admin,container,false)
        val postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        imageView = binding.root.findViewById(R.id.imageHolder)

        binding.backButton.setOnClickListener{
            findNavController().navigate(R.id.action_addPostAdmin_to_tipsAndKnowledge)
        }

        binding.uploadImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/jpeg"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        binding.postButton.setOnClickListener{
            val title: String = binding.postTitle.text.toString()
            val content: String = binding.postContent.text.toString()
            if(inputCheck(title,content) && imageView.drawable != null){
                //Create post object
//                val context = requireContext()
//                val drawable = ContextCompat.getDrawable(context, R.drawable.test)
//                val bitmap = (drawable as BitmapDrawable).bitmap
                val byteArray = TkImageConverter().convertImage(imageInBitmap)
//                val byteArray = TkImageConverter().convertImage(bitmap)
                // Add data to database
                val post = TkPostData(title,byteArray,content)
                postViewModel.addPost(post)
                //display succeed message
                Toast.makeText(requireContext(),"Post uploaded",Toast.LENGTH_LONG).show()
                //Navigate back to previous page
                findNavController().navigate(R.id.action_addPostAdmin_to_tipsAndKnowledge)
            }
            else{
                Toast.makeText(requireContext(),"Please fill in all necessary fields",Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    private fun inputCheck(title: String, content: String): Boolean {
        return !(TextUtils.isEmpty(title)) && !(TextUtils.isEmpty(content))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val imageUri = data?.data
            if(imageUri != null){
                imageView.setImageURI(imageUri)
                imageInBitmap = MediaStore.Images.Media.getBitmap(requireActivity()
                    .contentResolver, imageUri)
            }
        }
    }

}