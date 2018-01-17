package com.pukkaspice.web.common.image;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;

@RunWith(MockitoJUnitRunner.class)
public class CloudinaryImageUtilTest {
    
    @InjectMocks
    private CloudinaryImageUtil cloudinaryImageUtil;
    
    @Mock
    private Cloudinary cloudinary;
    
    @Mock
    private Uploader uploader;
    
    
    @Test
    public void should_not_delete_when_image_name_null() throws IOException {
        // GIVEN
        ImageInfo imageInfo = new ImageInfo(null, ImageCategory.RECIPE);
        // WHEN
        cloudinaryImageUtil.deleteImage(imageInfo);
        // THEN
        verifyZeroInteractions(cloudinary);
    }
    
    @Test
    public void should_not_delete_when_image_name_blank_string() throws IOException {
        // GIVEN
        ImageInfo imageInfo = new ImageInfo("", ImageCategory.RECIPE);
        // WHEN
        cloudinaryImageUtil.deleteImage(imageInfo);
        // THEN
        verifyZeroInteractions(cloudinary);
    }
    
    @Test
    public void should_not_delete_when_invalid_cloudinary_url_is_image_name() throws IOException {
        // GIVEN
        ImageInfo imageInfo = new ImageInfo("test-image-name.png", ImageCategory.RECIPE);
        // WHEN
        cloudinaryImageUtil.deleteImage(imageInfo);
        // THEN
        verifyZeroInteractions(cloudinary);
    }
    
    @Test
    public void should_delete_when_valid_cloudinary_url_is_image_name() throws IOException {
        // GIVEN
        ImageInfo imageInfo = new ImageInfo("http://res.cloudinary.com/pukkaspice/image/upload/v1467049548/thai-curry_axwn7b.png", ImageCategory.RECIPE);
        given(cloudinary.uploader()).willReturn(uploader);
        // WHEN
        cloudinaryImageUtil.deleteImage(imageInfo);
        // THEN
        verify(uploader).destroy(anyString(), anyMap());
    }

}
