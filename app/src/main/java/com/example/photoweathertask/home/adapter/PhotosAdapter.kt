package com.example.photoweathertask.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.domain.models.WeatherPhoto
import com.example.photoweathertask.R
import com.example.photoweathertask.base.BaseAdapter
import com.example.photoweathertask.base.BaseViewHolder
import com.example.photoweathertask.base.helper.LoadImage
import com.example.photoweathertask.databinding.PhotosItemBinding

class PhotosAdapter : BaseAdapter<WeatherPhoto>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<WeatherPhoto> {
        return ViewHolder(
            PhotosItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<WeatherPhoto>, position: Int) {
        holder.onBind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val view: PhotosItemBinding) :BaseViewHolder<WeatherPhoto>(view.root) {
        override fun onBind(item: WeatherPhoto) {
            LoadImage.loadImage(view.root.context,item.path,R.drawable.weather,view.storeimg)
            view.photoNameTxt.setText(item.name)
        }
    }


}