package com.pukkaspice.web.common.model.user;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pukkaspice.web.common.model.recipe.ImageLocation;

@SuppressWarnings("serial")
public class UserSummary implements Serializable {
    
    protected int userId;
    
    @Size(min=2, max=50, message="Invalid forename length")
    protected String forename;
    
    @Size(min=2, max=50, message="Invalid surname length")
    protected String surname;

    @Email
    @Size(min=3, max=200, message="Invalid email length")
    protected String emailAddress;
    
    @Size(max=500, message="Invalid profile description length")
    protected String profileDescription;
    
    protected String imageName;
    
    @NotNull
    protected ImageLocation imageLocation;
    
    protected String imageUrl;
    
    protected String imageBase64;

    
    public UserSummary() { }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public ImageLocation getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(ImageLocation imageLocation) {
        this.imageLocation = imageLocation;
    }
    
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }
    
    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }

    /**
     * Gets the image URL.
     */
    @JsonIgnore
    public void buildImageUrl() {
        if (imageLocation != null) {
            StringBuilder imageUrl = new StringBuilder();
            
            switch (imageLocation) {
            case STATIC:
                imageUrl.append("/resources/assets/images/profiles/");
                imageUrl.append(imageName);
                break;
            case DYNAMIC:
                imageUrl.append("/image/profiles/");
                imageUrl.append(imageName);
                break;
            case CLOUDINARY:
                imageUrl.append(imageName);
                break;
            default:
                break;
            }
            
            setImageUrl(imageUrl.toString());
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(userId)
            .hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserSummary) {
            UserSummary other = (UserSummary) obj;
            return new EqualsBuilder()
                .append(userId, other.userId)
                .isEquals();
        }

        return false;
    }
    
}
