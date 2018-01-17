package com.pukkaspice.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pukkaspice.web.common.image.LocalImageUtil;

@Controller
public class ImageController {
    
    //TODO fix this - put so that always gets image for blank image name
    @RequestMapping(value = "/image/{imageCategory}", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody 
    public byte[] getNoFile(@PathVariable("imageCategory") String imageCategory)  {
        try {
            LocalImageUtil imageUtil = new LocalImageUtil();
            BufferedImage image = imageUtil.getImage(imageCategory, "nofile");
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    
    @RequestMapping(value = "/image/{imageCategory}/{imageName}", method = RequestMethod.GET, produces = "image/png")
    @ResponseBody 
    public byte[] getFile(@PathVariable("imageCategory") String imageCategory, @PathVariable("imageName") String imageName)  {
        try {
            LocalImageUtil imageUtil = new LocalImageUtil();
            BufferedImage image = imageUtil.getImage(imageCategory, imageName);
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            ImageIO.write(image, "png", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
