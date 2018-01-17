package com.pukkaspice.web.common.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("IMAGE_STOREAGE_DEV")
public class LocalImageUtil implements ImageUtil {
	
	private String openshiftDataDir = System.getenv("OPENSHIFT_DATA_DIR");
	
    public String writeBase64Image(String imageString, ImageInfo imageInfo) {
        imageString = imageString.substring("data:image/png;base64,".length());
        
        String imageLocation = openshiftDataDir + System.getProperty("file.separator") + imageInfo.getImageCategory().getLocation();

        try {
            File folder = new File(imageLocation);
            if (!folder.exists()) {
                folder.mkdir();
            }
            
            File f = new File(imageLocation + System.getProperty("file.separator") + imageInfo.getImageName());

            f.createNewFile();
            OutputStream out = new FileOutputStream(f);
            byte[] decoded = Base64.decodeBase64(imageString);
            out.write(decoded);
            out.flush();
            out.close();
            return imageInfo.getImageName();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
	
	public String writeImage(InputStream imageInputStream, ImageInfo imageInfo) {
	    String imageLocation = openshiftDataDir + System.getProperty("file.separator") + imageInfo.getImageCategory().getLocation();
	    
		try {
			File folder = new File(imageLocation);
			if (!folder.exists()) {
				folder.mkdir();
			}
					
			File f = new File(imageLocation + System.getProperty("file.separator") +  imageInfo.getImageName());
			
			f.createNewFile();
			OutputStream out = new FileOutputStream(f);
			int read = 0;
			byte[] bytes = new byte[1024];
 
			while ((read = imageInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
			
			return imageInfo.getImageName();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public String cropImage(String imageName, int x, int y, int width, int height) {
//		try {
//			File imageFile = new File(memberProfilePhotoDir + System.getProperty("file.separator") + imageName);
//			File croppedImageFile = new File(memberProfilePhotoDir + System.getProperty("file.separator") + imageName);
//			
//			BufferedImage originalImage = Imaging.getBufferedImage(imageFile);
//			BufferedImage croppedImage = this.crop(originalImage, x, y, width, height);
//			
//			ImageFormat format = ImageFormats.PNG;
//			Map<String, Object> optionalParams = new HashMap<String, Object>();
//			Imaging.writeImage(croppedImage, croppedImageFile, format, optionalParams);
//			
//		} catch (ImageReadException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (ImageWriteException e) {
//			e.printStackTrace();
//		}
//		return imageName;
	    
	    return null;
	}
	
	public BufferedImage crop(BufferedImage image, int startX, int startY, int endX, int endY) {
		BufferedImage img = image.getSubimage(startX, startY, endX, endY); //fill in the corners of the desired crop location here
		BufferedImage copyOfImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics g = copyOfImage.createGraphics();
		g.drawImage(img, 0, 0, null);
		return copyOfImage; 
	}

	public BufferedImage getImage(String imageCategory, String imageName) {
	    String imageLocation = openshiftDataDir + System.getProperty("file.separator") + imageCategory;
	    
		File croppedImageFile = new File(imageLocation + System.getProperty("file.separator") + imageName + ".png");
		try {
		    if (croppedImageFile.isFile() == false) {
		        return createImageNotFound();
		    }
		    
			return Imaging.getBufferedImage(croppedImageFile);
		} catch (ImageReadException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
    public BufferedImage createImageNotFound() {
        BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);

        Graphics2D ig2 = bi.createGraphics();
        ig2.setPaint(Color.ORANGE);
        ig2.fillRect(0, 0, bi.getWidth(), bi.getHeight());

        Font font = new Font("TimesRoman", Font.BOLD, 24);
        ig2.setFont(font);
        String message = "Image Not Found";
        FontMetrics fontMetrics = ig2.getFontMetrics();
        int stringWidth = fontMetrics.stringWidth(message);
        int stringHeight = fontMetrics.getAscent();
        ig2.setPaint(Color.black);
        ig2.drawString(message, (500 - stringWidth) / 2, 500 / 2 + stringHeight / 4);

        return bi;
    }

    @Override
    public void deleteImage(ImageInfo imageInfo) {
        String imageLocation = openshiftDataDir + System.getProperty("file.separator") + imageInfo.getImageCategory().getLocation() + System.getProperty("file.separator") + imageInfo.getImageName();

        File image = new File(imageLocation);
        if (image.exists()) {
            image.delete();
        }

    }
	
}
