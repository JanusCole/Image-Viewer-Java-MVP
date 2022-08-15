package com.example.janus.imageviewer_kotlin.ImageSearch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.janus.imageviewer_kotlin.R
import com.example.janus.imageviewer_kotlin.databinding.ImageSearchFragmentBinding
import com.example.janus.imageviewer_kotlin.util.DisplayFormattedMessages

class ImageSearchFragment : Fragment(), ImageSearchContract.View {

    lateinit var imageSearchPresenter : ImageSearchContract.Presenter

    private lateinit var binding: ImageSearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ImageSearchFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageSearchPresenter = ImageSearchPresenter(this);

        binding.imageSearchButton.setOnClickListener(View.OnClickListener {
            searchImages(binding.imageSearchCriteriaEditText.text.toString())
        })

    }

    // When the user clicks the Search button, call the Presenter to perform the search on the text in the search field
    private fun searchImages (searchCriteria : String) {
        imageSearchPresenter.loadImages(searchCriteria);
    }

    override fun displayImagesUI(searchCriteria: String) {
        val navController = findNavController()
        val action = ImageSearchFragmentDirections.actionGoToMultipleImageDisplayScreen(searchCriteria)
        navController.navigate(action)
    }

    override fun displayNotFoundMessage() {
        DisplayFormattedMessages.displayErrorMessageAlertDialog(getString(R.string.not_found_message), layoutInflater, context!!);
    }

    override fun DisplayNetworkErrorMessage() {
        DisplayFormattedMessages.displayErrorMessageAlertDialog(getString(R.string.network_error_message), layoutInflater, context!!);
    }
}