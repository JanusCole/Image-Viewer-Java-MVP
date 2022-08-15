package com.example.janus.imageviewer_kotlin.singleimagedisplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.janus.imageviewer_kotlin.R
import com.example.janus.imageviewer_kotlin.databinding.SingleImageDisplayFragmentBinding
import com.squareup.picasso.Picasso

class SingleImageDisplayFragment : Fragment() {

    private lateinit var binding: SingleImageDisplayFragmentBinding
    private lateinit var singleImageDisplayViewModel: SingleImageDisplayViewModel

    val args : SingleImageDisplayFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SingleImageDisplayFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageId =  args.imageId
        val navController = findNavController()

        binding.singleImageDisplayReturnButton.setOnClickListener(View.OnClickListener {
            navController.navigate(
                R.id.action_go_to_multiple_image_display_screen)
        })
        singleImageDisplayViewModel = ViewModelProvider(this).get(SingleImageDisplayViewModel::class.java)

        binding.singleImageDisplayViewModel = singleImageDisplayViewModel
        binding.lifecycleOwner = this

        singleImageDisplayViewModel.getImage().observe(viewLifecycleOwner, Observer {
            Picasso.with(context).load(it.webformatURL).into(binding.singleImageImageView)
        })
        singleImageDisplayViewModel.loadImage(imageId)

    }
}