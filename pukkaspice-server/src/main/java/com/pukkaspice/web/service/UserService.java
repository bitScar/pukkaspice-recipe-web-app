package com.pukkaspice.web.service;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pukkaspice.web.common.image.ImageCategory;
import com.pukkaspice.web.common.image.ImageInfo;
import com.pukkaspice.web.common.image.ImageUtil;
import com.pukkaspice.web.common.jersey.InsuficientPermissionsException;
import com.pukkaspice.web.common.model.recipe.ImageLocation;
import com.pukkaspice.web.common.model.user.UserSummary;
import com.pukkaspice.web.common.security.UserRole;
import com.pukkaspice.web.dao.mybatis.mappers.UserMapper;

@Service("UserService")  
@Repository 
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private SecurityService securityService;
    
    @Autowired
    private ImageUtil imageUtil;
    
    
    public UserSummary getUserById(int userId) {
        UserSummary userSummary = userMapper.getUserById(userId);
        userSummary.buildImageUrl();
        
        return userSummary; 
    }
    
    public UserSummary getUserByEmailAddress(String emailAddress, String hashedPassword) {
        UserSummary userSummary = userMapper.getUserSummaryByEmailAddress(emailAddress, hashedPassword);
        if (userSummary != null) {
            userSummary.buildImageUrl();
        }
        
        return userSummary;
    }

    public List<UserRole> getUserRoles(UserSummary userSummary) {
        return userMapper.getUserRoles(userSummary.getUserId());
    }
    
    @Transactional
    public void insertUserRole(UserSummary userSummary, List<UserRole> userRoles) {
        for (UserRole userRole : userRoles) {
            userMapper.insertUserRole(userSummary.getUserId(), userRole);
        }
    }

    @Transactional
    public UserSummary saveUser(UserSummary userSummary) {
        String imageBase64 = userSummary.getImageBase64();
        boolean imageHasBeenUploaded = imageBase64 != null && !imageBase64.trim().equals("");
        if (imageHasBeenUploaded) {
            ImageInfo imageInfo = new ImageInfo(RandomStringUtils.randomAlphanumeric(8) + ".png", ImageCategory.PROFILE);
            String newImageUrl = imageUtil.writeBase64Image(imageBase64, imageInfo);
            
            ImageInfo imageInfoToDelete = new ImageInfo(userSummary.getImageName(), ImageCategory.PROFILE);
            imageUtil.deleteImage(imageInfoToDelete);
            
            userSummary.setImageName(newImageUrl);
            userSummary.setImageLocation(ImageLocation.DYNAMIC);
        }
        
        if (securityService.canSave(userSummary)) {
            userMapper.updateUserSummary(userSummary);
        } else {
            throw new InsuficientPermissionsException();
        }
        
        return userSummary;
    }

}
