package com.pukkaspice.web.common.image;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Component
@Profile("IMAGE_STOREAGE_PROD")
public class CloudinaryImageUtil implements ImageUtil {
    
    @Autowired
    private Cloudinary cloudinary;
    
    @Override
    public String writeBase64Image(String imageString, ImageInfo imageInfo) {
        String imageUrl = null;
        
        try {
            Map<?, ?> upload = cloudinary.uploader().upload(imageString, ObjectUtils.emptyMap());
            imageUrl = (String) upload.get("url");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error uploading to Cloudinary");
        }
        
        return imageUrl;
    }

    @Override
    public void deleteImage(ImageInfo imageInfo) {
        String publicId = extractPublicId(imageInfo);
        
        try {
            if (publicId.equals("") == false) {
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting from Cloudinary");
        }
    }
    
    private String extractPublicId(ImageInfo imageInfo) {
        String imageName = imageInfo.getImageName();
        
        if (imageName != null && imageName.contains("//res.cloudinary.com/pukkaspice")) {
            String[] imageNameSplit = imageName.split("\\/");
            String publicImageName = imageNameSplit[imageNameSplit.length - 1];
            String publicId = publicImageName.substring(0, publicImageName.indexOf('.'));
            return publicId;
        } else {
            return "";
        }
    }

}
