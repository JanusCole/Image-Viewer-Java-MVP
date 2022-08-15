package com.example.janus.imageviewer_kotlin.MultipleImageDisplay

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.janus.imageviewer_kotlin.R
import com.example.janus.imageviewer_kotlin.data.models.ImageDescription
import com.example.janus.imageviewer_kotlin.databinding.MultipleImagesDisplayFragmentBinding

class MultipleImageDisplayFragment : Fragment() {

    private lateinit var binding: MultipleImagesDisplayFragmentBinding
    private lateinit var multipleImageDisplayViewModel: MultipleImageDisplayViewModel

    lateinit var imagesRecyclerView : RecyclerView
    lateinit var  gridLayoutManager : GridLayoutManager
    lateinit var  imagesRecyclerViewAdapter : MultipleImageDisplayRecyclerViewAdapter

    val args : MultipleImageDisplayFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MultipleImagesDisplayFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchCriteria: String = args.searchCriteria

        val navController = findNavController()

        binding.multipleImagesDisplayFragmentReturnButton.setOnClickListener(View.OnClickListener {
            navController.navigate(
                R.id.action_go_to_image_search_screen)
        })
        multipleImageDisplayViewModel = ViewModelProvider(this).get(MultipleImageDisplayViewModel::class.java)

        // Get the display fields from the layout
        imagesRecyclerView = binding.multipleImagesDisplayFragmentRecyclerView
        gridLayoutManager = GridLayoutManager(
            activity,
            getResources().getInteger(R.integer.image_grid_columns)
        );
        imagesRecyclerView.setLayoutManager(gridLayoutManager);
        imagesRecyclerViewAdapter = MultipleImageDisplayRecyclerViewAdapter(ArrayList<ImageDescription>(),
            context!!,
            object: MultipleImageDisplayRecyclerViewAdapter.OnItemClickedListener{
                override fun onItemClicked(view: View, imageIndex: Int) {
                    val action = MultipleImageDisplayFragmentDirections.actionGoToSingleImageDisplayScreen(imageIndex)
                    navController.navigate(action)
                }
            });
        imagesRecyclerView.setAdapter(imagesRecyclerViewAdapter);

        multipleImageDisplayViewModel.getImages().observe(viewLifecycleOwner, Observer {
            imagesRecyclerViewAdapter.addImageURLs(it)
        })
        multipleImageDisplayViewModel.loadImages(searchCriteria)

    }
}