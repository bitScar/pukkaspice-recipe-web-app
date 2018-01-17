package com.pukkaspice.web.common.image;

public class ImageInfo {

	private String imageName;
	
	private ImageCategory imageCategory;
	

	public ImageInfo(String imageName, ImageCategory imageLocation) {
        super();
        this.imageName = imageName;
        this.imageCategory = imageLocation;
    }

    public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	public String getImageName() {
		return imageName;
	}

    public ImageCategory getImageCategory() {
        return imageCategory;
    }

    public void setImageCategory(ImageCategory imageCategory) {
        this.imageCategory = imageCategory;
    }
	
}
