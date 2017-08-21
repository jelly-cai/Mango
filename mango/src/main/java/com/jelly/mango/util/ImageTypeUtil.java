package com.jelly.mango.util;

import com.jelly.mango.MultiplexImage;

import java.util.ArrayList;
import java.util.List;

/**
 * MultiplexImage operating util
 * Created by Jelly on 2017/8/14.
 */

public class ImageTypeUtil {

    private static final String TAG = "ImageTypeUtil";

    /**
     * get image list
     * @param images
     * @param types
     * @return
     * @throws Exception
     */
    public static List<MultiplexImage> getImagesList(List<String> images, List<Integer> types) throws Exception{
        if(images.size() != types.size()) {
            throw new RuntimeException("长度必须相同");
        }

        MultiplexImage multiplexImage = null;
        List<MultiplexImage> multiplexImages = new ArrayList<MultiplexImage>();

        for (int i=0;i<images.size();i++){
            int imageType = types.get(i);
            if(!isImageType(imageType)) return null;
            multiplexImage = new MultiplexImage(images.get(i), imageType);
            multiplexImages.add(multiplexImage);
        }

        return multiplexImages;
    }

    /**
     * get image list
     * @param images
     * @param imageType
     * @return
     */
    public static List<MultiplexImage> getImageList(List<String> images,int imageType){

        if(!isImageType(imageType)) return null;

        MultiplexImage multiplexImage = null;
        List<MultiplexImage> multiplexImages = new ArrayList<MultiplexImage>();
        for (String img:images){
            multiplexImage = new MultiplexImage(img, imageType);
            multiplexImages.add(multiplexImage);
        }
        return multiplexImages;
    }

    /**
     * get normal list
     * @param images
     * @return
     */
    public static List<MultiplexImage> getNormalImageList(List<String> images) {
        return getImageList(images, MultiplexImage.ImageType.NORMAL);
    }

    /**
     * get file list
     * @param images
     * @return
     */
    public static List<MultiplexImage> getFileImageList(List<String> images) {
        return getImageList(images, MultiplexImage.ImageType.FILE);
    }

    /**
     * get gif list
     * @param images
     * @return
     */
    public static List<MultiplexImage> getGIFImageList(List<String> images) {
        return getImageList(images, MultiplexImage.ImageType.GIF);
    }

    /**
     * check is belong ture image type
     * @param imageType
     * @return
     */
    public static boolean isImageType(int imageType){
        if(imageType != MultiplexImage.ImageType.NORMAL && imageType != MultiplexImage.ImageType.FILE && imageType != MultiplexImage.ImageType.GIF){
            throw new RuntimeException("error image type");
        }else{
            return true;
        }
    }

    public static MultiplexImage getImage(String path,int imageType){
        if(!isImageType(imageType)) return null;
        return new MultiplexImage(path,imageType);
    }

}


