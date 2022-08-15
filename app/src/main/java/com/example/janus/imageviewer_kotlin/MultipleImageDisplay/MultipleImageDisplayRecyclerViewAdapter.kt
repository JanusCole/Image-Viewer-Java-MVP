package com.example.janus.imageviewer_kotlin.MultipleImageDisplay

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.janus.imageviewer_kotlin.R
import com.example.janus.imageviewer_kotlin.data.models.ImageDescription
import com.example.janus.imageviewer_kotlin.databinding.MultipleImageDisplayRecyclerviewItemBinding
import com.example.janus.imageviewer_kotlin.util.SquareImageView
import com.squareup.picasso.Picasso

class MultipleImageDisplayRecyclerViewAdapter (val images : MutableList<ImageDescription>, val context : Context, val onItemClickedListener : MultipleImageDisplayRecyclerViewAdapter.OnItemClickedListener) : RecyclerView.Adapter<MultipleImageDisplayRecyclerViewAdapter.ViewHolder>() {

    // Allows for adding new item to the List o fimage URLs. This is to support pagination
    fun addImageURLs(newImages : List<ImageDescription>) {
        Log.d("XXXXXX", "New addImageURLs " + newImages.size)
        images.addAll(newImages);
        Log.d("XXXXXX", "New addImageURLs images " + images.size)
        //notifyDataSetChanged();
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("XXXXXX", "New onCreateViewHolder ")
        val view = MultipleImageDisplayRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context)).root
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("XXXXXX", "New onBindViewHolder ")
        //holder.image.setImageResource(R.drawable.ic_green_button)
        Picasso.with(context).load(images.get(position).webformatURL).into(holder.image)
        val itemNumber = position
        holder.image.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                onItemClickedListener.onItemClicked(p0!!, images.get(itemNumber).id)
            }

        })

    }

/*    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameButtonViewHolder {
        val buttonCardView = GameButtonCardBinding.inflate(LayoutInflater.from(parent.context)).root
        return GameButtonViewHolder(buttonCardView)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holderGameButton: GameButtonViewHolder, position: Int) {
        holderGameButton.gameButtonImage.setImageResource(gameButtonColors.get(position).imageResource)
        holderGameButton.gameButtonImage.setOnTouchListener { _, event ->
            onGameButtonTouchListener.onTouchGameButton(holderGameButton.adapterPosition, event)
            true
        }
    }*/

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder (itemView : View) : RecyclerView.ViewHolder (itemView) {
        val image = itemView.findViewById<SquareImageView>(R.id.multipleImagesRecyclerViewImageView)
    }

    // Passes back the index of the image in the List.
    interface OnItemClickedListener {
        fun onItemClicked(view : View, imageIndex : Int) : Unit
    }
}
