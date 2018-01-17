package com.pukkaspice.web.common.image;


public interface ImageUtil {
    
    public String writeBase64Image(String imageString, ImageInfo imageInfo);

	public void deleteImage(ImageInfo imageInfo);
	
}
