package com.codepath.example.gridimagesearch;

import java.util.List;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {

	public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.item_image_result,images);
 	}

//translations step. converting a data source(image result java object) and converting to view
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo=this.getItem(position);
		SmartImageView ivImage;
		if(convertView==null){
			//layoutinflator will convert into in memory object view
			LayoutInflater inflator= LayoutInflater.from(getContext());
			ivImage = (SmartImageView) inflator.inflate(R.layout.item_image_result,parent,false);
		} else {  //reuse view
			ivImage=(SmartImageView) convertView;
			ivImage.setImageResource(android.R.color.transparent);
		}
		
		ivImage.setImageUrl(imageInfo.getThumbUrl());
		return ivImage;
		 
	}

	
}
