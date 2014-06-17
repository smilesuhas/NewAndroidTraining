package com.codepath.example.gridimagesearch;

import java.io.Serializable;

public class Pass_On implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6283135313524940047L;
	public static String   ImageSize;
	public static String ImageType;
	public static String ImageColor;
	public static String ImageSite;
 
 	public Pass_On(String ImageSize, String ImageColor, String ImageType, String ImageSite) {
		Pass_On.ImageSize=ImageSize;
		Pass_On.ImageType=ImageType;
		Pass_On.ImageColor=ImageColor;
		Pass_On.ImageSite=ImageSite;
	}
 
}
